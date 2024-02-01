package org.example.richard.demo.models

import arrow.core.Either
import arrow.core.Predicate
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.richard.demo.predicates.NonEmptyStringPredicate
import org.example.richard.demo.predicates.and
import org.example.richard.demo.predicates.ShorterThanPredicate
import org.example.richard.demo.show.Show

@JvmInline
value class FirstName private constructor(val v: String) {
    companion object {
        fun ofWithPredicate(p: Predicate<String>): (String) -> Either<InvalidFirstName, FirstName> = { v: String ->
            either {
                ensure(p(v)) { InvalidFirstName(v) }
                FirstName(v)
            }
        }

        fun of(v: String): Either<InvalidFirstName, FirstName> = ofWithPredicate(
            and(ShorterThanPredicate(10))(
                NonEmptyStringPredicate
            )
        )(v)
    }
}

val InvalidFirstNameShow: Show<InvalidFirstName> = object : Show<InvalidFirstName> {
    override fun show(v: InvalidFirstName): String = "The given first name: '${v.invalidName}' is invalid."
}