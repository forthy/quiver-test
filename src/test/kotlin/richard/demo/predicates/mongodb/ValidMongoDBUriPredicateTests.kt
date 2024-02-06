package richard.demo.predicates.mongodb

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.example.richard.demo.predicates.mongodb.ValidMongoDBUriPredicate

class ValidMongoDBUriPredicateTests : FunSpec({
    test("ValidMongoDBUriPredicate should properly verify a string for a valid MongoDB URI") {
        ValidMongoDBUriPredicate("").shouldBeFalse()
        ValidMongoDBUriPredicate("hello").shouldBeFalse()
        ValidMongoDBUriPredicate("mongodb://richard:password@test.com:7732").shouldBeTrue()
    }
})
