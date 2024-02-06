package richard.demo.config

import arrow.core.left
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.richard.demo.config.InvalidDBUri
import org.example.richard.demo.config.MongoDBUri
import org.example.richard.demo.predicates.NonEmptyStringPredicate
import org.example.richard.demo.predicates.and
import org.example.richard.demo.predicates.mongodb.ValidMongoDBUriPredicate

class MongoDBUriTests : FunSpec({
    test("Should properly construct a MongoDBUri") {
        val c = MongoDBUri.ofWithPredicate(and(NonEmptyStringPredicate)(ValidMongoDBUriPredicate))

        c("").shouldBe(InvalidDBUri("").left())
        c("hello").shouldBe(InvalidDBUri("hello").left())
        c("mongodb://richard:password@test.com:7732").getOrNone()
            .fold({ shouldFail { println("The MongoDB URI should be valid") } },
                { uri -> uri.v.shouldBe("mongodb://richard:password@test.com:7732") })
    }
})