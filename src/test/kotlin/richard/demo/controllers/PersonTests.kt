package richard.demo.controllers

import org.http4k.core.Body
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PersonTests {
    @Test
    fun `bodyParsingErrorShow should properly convert BodyParsingError to a string`() {
       val b1 = BodyParsingError(Body(""), Throwable(message = null))
        Assertions.assertEquals("Parsing error - body:\n''", bodyParsingErrorShow.show(b1))
    }
}