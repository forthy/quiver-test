package richard.demo.config

import arrow.core.left
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import richard.demo.predicates.NonEmptyStringPredicate
import richard.demo.predicates.and
import richard.demo.predicates.mongodb.ValidMongoDBUriPredicate

class MongoDBUriTests {
    @Test
    fun `Should properly construct a MongoDBUri`() {
        val c = MongoDBUri.ofWithPredicate(and(NonEmptyStringPredicate)(ValidMongoDBUriPredicate))

        Assertions.assertEquals(InvalidDBUri("").left(), c(""))
        Assertions.assertEquals(InvalidDBUri("hello").left(), c("hello"))
        c("mongodb://richard:password@test.com:7732").getOrNone()
            .fold({ fail { "The MongoDB URI should be valid" } },
                { uri -> Assertions.assertEquals("mongodb://richard:password@test.com:7732", uri.v) })
    }
}