package org.example.richard.demo.config

import arrow.core.Either
import arrow.core.Predicate
import arrow.core.raise.either
import arrow.core.raise.ensure

data class MongoDBUri private constructor(val v: String) {
    companion object {
        fun ofWithPredicate(p: Predicate<String>): (String) -> Either<InvalidDBUri, MongoDBUri> = { c ->
            either {
                ensure(p(c)) { InvalidDBUri(c) }
                MongoDBUri(c)
            }
        }
    }
}