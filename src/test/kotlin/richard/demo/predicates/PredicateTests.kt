package richard.demo.predicates

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.example.richard.demo.predicates.*

class PredicateTests : FunSpec( {
    test("NonEmptyStringPredicate should properly verify non-empty string cases") {
        NonEmptyStringPredicate("").shouldBeFalse()
        NonEmptyStringPredicate("test").shouldBeTrue()
    }

    test("ShorterThanPredicate should properly verify the given string based on the defined char length") {
        val p = ShorterThanPredicate(5)

        // TODO - how about the string length is zero?
        p("").shouldBeFalse()
        p("abcd").shouldBeTrue()
        p("abcde").shouldBeTrue()
        p("abcdef").shouldBeFalse()
    }

    test("Verify 'and' operation") {
        val p = and(NonEmptyStringPredicate)(ShorterThanPredicate(5))

        p("").shouldBeFalse()
        p("abcd").shouldBeTrue()
        p("abcdef").shouldBeFalse()
    }

    test("Verify 'or' operation") {
        val p = or(NonEmptyStringPredicate)(ShorterThanPredicate(5))

        // TODO - Till this case, I realised the functional spec of 'ShorterThanPredicate' should include the lower bound (> 0)
        p("").shouldBeFalse()
        p("abcd").shouldBeTrue()
        p("abcdef").shouldBeTrue()
    }

    test("Verify 'not' operation") {
        not(NonEmptyStringPredicate)("").shouldBeTrue()
        not(NonEmptyStringPredicate)("abc").shouldBeFalse()

        val p = ShorterThanPredicate(5)

        not(p)("").shouldBeTrue()
        not(p)("abcd").shouldBeFalse()
        not(p)("abcde").shouldBeFalse()
        not(p)("abcdef").shouldBeTrue()
    }
})