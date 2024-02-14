package richard.demo.controllers

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import io.quarkus.test.junit.QuarkusTest
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

@QuarkusTest
class AppTests {
    @Test
    fun `App says hello!`() {
        val app = App()
        
        assertThat(app(Request(GET, "/hello")), hasStatus(OK) and hasBody("hello quarkus world!"))
    }
}