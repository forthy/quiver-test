package richard.demo.models

import arrow.core.left
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.richard.demo.models.InvalidLastName
import org.example.richard.demo.models.LastName

class LastNameTests : FunSpec({
    test("LastName should be properly constructed") {
        LastName.of("").shouldBe(InvalidLastName("").left())

        LastName.of("Longjohns")
            .fold({ _ -> shouldFail { println("The last name should be valid") } }, { n -> n.v.shouldBe("Longjohns") })
    }
})