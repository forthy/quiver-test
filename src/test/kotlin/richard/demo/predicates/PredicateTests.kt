package richard.demo.predicates

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.example.richard.demo.predicates.*

class PredicateTests : FunSpec({
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

    test("IPv4Predicate should properly verify a given string for a possible IPv4 IP") {
        IPv4Predicate("255.120.223.13").shouldBeTrue()
        IPv4Predicate("127.0.0.1").shouldBeTrue()
        IPv4Predicate("").shouldBeFalse()
    }
    test("IPv6Predicate should properly verify a given string for a possible IPv6 IP") {
        IPv6Predicate("2001:db8:3333:4444:5555:6666:7777:8888").shouldBeTrue()
        IPv6Predicate("2001:db8::").shouldBeTrue()
        IPv6Predicate("::").shouldBeTrue()
        IPv6Predicate("").shouldBeFalse()
    }
})