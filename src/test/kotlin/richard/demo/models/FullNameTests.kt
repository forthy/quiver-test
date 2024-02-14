package richard.demo.models

import arrow.core.Either
import arrow.core.none
import arrow.core.uncurried
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class FullNameTests {
    @Test
    fun `FullName should be properly constructed`() {
        Either.zipOrAccumulate(FirstName.of("Richard"), LastName.of("Chuo"), FullName.withoutMiddleNameOf().uncurried())
            .fold(
                { _ -> fail { "FullName construction failed" } },
                { v ->
                    Assertions.assertEquals(none<MiddleName>(), v.mn)
                    Assertions.assertEquals("Richard", v.fn.v)
                    Assertions.assertEquals("Chuo", v.ln.v)
                }
            )

        Either.zipOrAccumulate(
            FirstName.of("Richard"),
            LastName.of("Chuo"),
            FullName.of(MiddleName.of("Simon").getOrNone()).uncurried()
        ).fold(
            { _ -> fail { "FullName construction failed" } },
            { v ->
                v.mn.fold(
                    { fail { "Middle name should exist" } },
                    { mName -> Assertions.assertEquals("Simon", mName.v) })
                Assertions.assertEquals("Richard", v.fn.v)
                Assertions.assertEquals("Chuo", v.ln.v)
            }
        )
    }
}