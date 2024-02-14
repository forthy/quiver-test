package richard.demo.predicates

import com.mongodb.assertions.Assertions
import org.junit.jupiter.api.Test

class PredicateTests {
    @Test
    fun`NonEmptyStringPredicate should properly verify non-empty string cases`() {
        Assertions.assertFalse(NonEmptyStringPredicate(""))
        Assertions.assertTrue(NonEmptyStringPredicate("test"))
    }

    @Test
    fun`ShorterThanPredicate should properly verify the given string based on the defined char length`() {
        val p = ShorterThanPredicate(5)

        // TODO - how about the string length is zero?
        Assertions.assertFalse(p(""))
        Assertions.assertTrue(p("abcd"))
        Assertions.assertTrue(p("abcde"))
        Assertions.assertFalse(p("abcdef"))
    }

    fun`Verify 'and' operation`() {
        val p = and(NonEmptyStringPredicate)(ShorterThanPredicate(5))

        Assertions.assertFalse(p(""))
        Assertions.assertTrue(p("abcd"))
        Assertions.assertFalse(p("abcdef"))
    }

    fun`Verify 'or' operation`() {
        val p = or(NonEmptyStringPredicate)(ShorterThanPredicate(5))

        // TODO - Till this case, I realised the functional spec of 'ShorterThanPredicate' should include the lower bound (> 0)
        Assertions.assertFalse(p(""))
        Assertions.assertTrue(p("abcd"))
        Assertions.assertTrue(p("abcdef"))
    }

    fun`Verify 'not' operation`() {
        Assertions.assertTrue(not(NonEmptyStringPredicate)(""))
        Assertions.assertFalse(not(NonEmptyStringPredicate)("abc"))

        val p = ShorterThanPredicate(5)

        Assertions.assertTrue(not(p)(""))
        Assertions.assertFalse(not(p)("abcd"))
        Assertions.assertFalse(not(p)("abcde"))
        Assertions.assertTrue(not(p)("abcdef"))
    }

    fun`IPv4Predicate should properly verify a given string for a possible IPv4 IP`() {
        Assertions.assertTrue(IPv4Predicate("255.120.223.13"))
        Assertions.assertTrue(IPv4Predicate("127.0.0.1"))
        Assertions.assertFalse(IPv4Predicate(""))
    }
    fun`IPv6Predicate should properly verify a given string for a possible IPv6 IP`() {
        Assertions.assertTrue(IPv6Predicate("2001:db8:3333:4444:5555:6666:7777:8888"))
        Assertions.assertTrue(IPv6Predicate("2001:db8::"))
        Assertions.assertTrue(IPv6Predicate("::"))
        Assertions.assertFalse(IPv6Predicate(""))
    }
}