package richard.demo.models

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.richard.demo.models.InvalidFirstName
import org.example.richard.demo.models.InvalidFirstNameShow

class FirstNameTests : FunSpec({
    test("InvalidFirstNameShow should generate required message") {
        InvalidFirstNameShow.show(InvalidFirstName("Tooshort")).shouldBe("The given first name: 'Tooshort' is invalid.")
    }
})