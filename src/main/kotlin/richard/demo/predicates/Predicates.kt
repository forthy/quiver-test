package org.example.richard.demo.predicates

import arrow.core.Predicate


val NonEmptyStringPredicate: Predicate<String> = fun(v: String): Boolean = v.isNotEmpty()

val ShorterThanPredicate: (Int) -> Predicate<String> =
    fun(n: Int): Predicate<String> = fun(v: String): Boolean = v.length in 1..n
