package richard.demo.predicates.mongodb

import com.mongodb.assertions.Assertions
import org.junit.jupiter.api.Test

class ValidMongoDBUriPredicateTests {
    @Test
    fun `ValidMongoDBUriPredicate should properly verify a string for a valid MongoDB URI`() {
        Assertions.assertFalse(ValidMongoDBUriPredicate(""))
        Assertions.assertFalse(ValidMongoDBUriPredicate("hello"))
        Assertions.assertTrue(ValidMongoDBUriPredicate("mongodb://richard:password@test.com:7732"))
    }
}
