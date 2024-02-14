package richard.demo.models

import arrow.core.left
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class LastNameTests {
    @Test
    fun `LastName should be properly constructed`() {
        Assertions.assertEquals(InvalidLastName("").left(), LastName.of(""))

        LastName.of("Longjohns")
            .fold({ _ -> fail { "The last name should be valid" } }, { n -> Assertions.assertEquals("Longjohns", n.v) })
    }
}