package richard.demo.repo.config

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.memoize
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.richard.demo.config.*
import org.example.richard.demo.predicates.IPv4Predicate
import org.example.richard.demo.predicates.UnregisteredTCPPortPredicate
import org.example.richard.demo.repo.config.EnvConfigRepo

class ServiceAddressTests : FunSpec({
    test("Should properly construct ServiceAddress") {
        val envM: Dotenv by lazy { dotenv() }

        val ipE = EnvConfigRepo.readAs<String> { envM }("IP").toEither { ConfigNotDefined("IP") }.flatMap { ip ->
            IP.ofWithPredicate(IPv4Predicate)(ip)
        }
        val portE =
            EnvConfigRepo.readAs<Int> { envM }("TCP_PORT").toEither { ConfigNotDefined("TCP_PORT") }.flatMap { port ->
                TCPPort.ofWithPredicate(UnregisteredTCPPortPredicate)(port)
            }

        Either.zipOrAccumulate(ipE, portE, ::ServiceAddress)
            .fold({ _ -> shouldFail { println("ServiceAddress failed to be constructed") } }, { sa ->
                run {
                    sa.ip.v.shouldBe("127.0.0.1")
                    sa.port.v.shouldBe(800)
                }
            })
    }
})