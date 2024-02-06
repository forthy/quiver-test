package richard.demo.models

import arrow.core.Either
import arrow.core.none
import arrow.core.uncurried
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.richard.demo.models.FirstName
import org.example.richard.demo.models.FullName
import org.example.richard.demo.models.LastName
import org.example.richard.demo.models.MiddleName

class FullNameTests : FunSpec({
    test("FullName should be properly constructed") {
        Either.zipOrAccumulate(FirstName.of("Richard"), LastName.of("Chuo"), FullName.withoutMiddleNameOf().uncurried())
            .fold(
                { _ -> shouldFail { println("FullName construction failed") } },
                { v ->
                    v.mn.shouldBe(none())
                    v.fn.v.shouldBe("Richard")
                    v.ln.v.shouldBe("Chuo")
                }
            )

        Either.zipOrAccumulate(
            FirstName.of("Richard"),
            LastName.of("Chuo"),
            FullName.of(MiddleName.of("Simon").getOrNone()).uncurried()
        ).fold(
            { _ -> shouldFail { println("FullName construction failed") } },
            { v ->
                v.mn.fold(
                    { shouldFail { println("Middle name should exist") } },
                    { mName -> mName.v.shouldBe("Simon") })
                v.fn.v.shouldBe("Richard")
                v.ln.v.shouldBe("Chuo")
            }
        )
    }
})