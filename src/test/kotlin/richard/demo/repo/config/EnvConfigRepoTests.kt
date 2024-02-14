package richard.demo.repo.config

import io.github.cdimascio.dotenv.dotenv
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class EnvConfigRepoTests {
    @Test
    fun `Should read a configuration as a string`() {
        val envM by lazy { dotenv() }

        EnvConfigRepo.readAs<String> { envM }("MONGODB_URI").fold({ fail { "MONGODB_URI should be available" } },
            { uri -> Assertions.assertEquals("mongodb://richard:password@test.com:7732", uri) })

        EnvConfigRepo.readAs<Int> { envM }("TCP_PORT").fold(
            { fail { "TCP_PORT should be available" } },
            { port -> Assertions.assertEquals(800, port) }
        )
    }
}