package org.example.richard.demo.models

import arrow.core.Either
import arrow.core.Predicate
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.richard.demo.predicates.NonEmptyStringPredicate
import org.example.richard.demo.predicates.ShorterThanPredicate
import org.example.richard.demo.predicates.and

@JvmInline
value class LastName private constructor(val v: String) {
    companion object {
        fun ofWithPredicate(p: Predicate<String>): (String) -> Either<InvalidLastName, LastName> = { v ->
            either {
                ensure(p(v)) { InvalidLastName(v) }
                LastName(v)
            }
        }

        fun of(v: String): Either<InvalidLastName, LastName> = ofWithPredicate(
            and(ShorterThanPredicate(10))(
                NonEmptyStringPredicate
            )
        )(v)
    }
}