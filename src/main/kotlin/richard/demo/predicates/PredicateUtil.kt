package org.example.richard.demo.predicates

import arrow.core.Predicate

fun <T> and(p: Predicate<T>): (Predicate<T>) -> Predicate<T> {
    return { p1 ->
        fun(v: T): Boolean { return p1(v) && p(v) }
    }
}

fun <T> or(p: Predicate<T>): (Predicate<T>) -> Predicate<T> {
    return { p1 ->
        fun(v: T): Boolean { return p1(v) || p(v) }
    }
}

fun <T> not(p: Predicate<T>): Predicate<T> = fun(v: T): Boolean { return !p(v) }