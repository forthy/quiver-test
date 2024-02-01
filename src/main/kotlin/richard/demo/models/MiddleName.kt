package org.example.richard.demo.models

import arrow.core.Either
import arrow.core.Predicate
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.richard.demo.predicates.NonEmptyStringPredicate
import org.example.richard.demo.predicates.ShorterThanPredicate
import org.example.richard.demo.predicates.and

@JvmInline
value class MiddleName private constructor(val v: String) {
    companion object {
        fun ofWithPredicate(p: Predicate<String>): (String) -> Either<InvalidMiddleName, MiddleName> = { v ->
            either {
                ensure(p(v)) { InvalidMiddleName(v) }
                MiddleName(v)
            }
        }

        fun of(v: String): Either<InvalidMiddleName, MiddleName> = ofWithPredicate(
            and(NonEmptyStringPredicate)(
                ShorterThanPredicate(10)
            )
        )(v)
    }
}