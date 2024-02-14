package richard.demo.predicates.mongodb

import arrow.core.Option
import arrow.core.Predicate
import com.mongodb.ConnectionString

val ValidMongoDBUriPredicate: Predicate<String> = fun(v: String): Boolean {
    return Option.catch { ConnectionString(v) }.fold({ false }, { _ -> true })
}