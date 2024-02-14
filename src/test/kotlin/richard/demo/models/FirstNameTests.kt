package richard.demo.models

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FirstNameTests {
    @Test
    fun `InvalidFirstNameShow should generate required message`() {
        Assertions.assertEquals("The given first name: 'Tooshort' is invalid.", InvalidFirstNameShow.show(InvalidFirstName("Tooshort")))
    }
}