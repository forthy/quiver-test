package richard.demo.repo.config

import arrow.core.memoize
import io.github.cdimascio.dotenv.dotenv
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.richard.demo.repo.config.EnvConfigRepo

class EnvConfigRepoTests : FunSpec({
    test("Should read a configuration as a string") {
        val envM = { dotenv() }.memoize()
        
        EnvConfigRepo.readAs<String>(envM)("MONGODB_URI").fold({ shouldFail { println("MONGODB_URI should be available") } },
            { uri -> uri.shouldBe("mongodb://richard:password@test.com:7732") })

        EnvConfigRepo.readAs<Int>(envM)("TCP_PORT").fold(
            { shouldFail { println("TCP_PORT should be available") } },
            { port -> port.shouldBe(800) }
        )
    }
})