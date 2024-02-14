package richard.demo.repo.config

import arrow.core.Either
import arrow.core.flatMap
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import richard.demo.config.ConfigNotDefined
import richard.demo.config.IP
import richard.demo.config.ServiceAddress
import richard.demo.config.TCPPort
import richard.demo.predicates.IPv4Predicate
import richard.demo.predicates.UnregisteredTCPPortPredicate

class ServiceAddressTests {
    @Test
    fun`Should properly construct ServiceAddress`() {
        val envM: Dotenv by lazy { dotenv() }

        val ipE = EnvConfigRepo.readAs<String> { envM }("IP").toEither { ConfigNotDefined("IP") }.flatMap { ip ->
            IP.ofWithPredicate(IPv4Predicate)(ip)
        }
        val portE =
            EnvConfigRepo.readAs<Int> { envM }("TCP_PORT").toEither { ConfigNotDefined("TCP_PORT") }.flatMap { port ->
                TCPPort.ofWithPredicate(UnregisteredTCPPortPredicate)(port)
            }

        Either.zipOrAccumulate(ipE, portE, ::ServiceAddress)
            .fold({ _ -> fail { "ServiceAddress failed to be constructed" } }, { sa ->
                run {
                    Assertions.assertEquals("127.0.0.1", sa.ip.v)
                    Assertions.assertEquals(800, sa.port.v)
                }
            })
    }
}