package org.example

import arrow.core.Either
import org.example.richard.demo.models.FirstName
import org.example.richard.demo.models.InvalidFirstNameShow
import org.example.richard.demo.predicates.NonEmptyStringPredicate
import org.example.richard.demo.predicates.and
import org.example.richard.demo.predicates.ShorterThanPredicate

fun main(args: Array<String>) {
    println("Hello World!")
    
    println(FirstName.of("Richard"))
    println(FirstName.ofWithPredicate(ShorterThanPredicate(6))("Richard"))

    val p = and(ShorterThanPredicate(6))(NonEmptyStringPredicate)
    when(val x = FirstName.ofWithPredicate(p)("Tooshort")) {
        is Either.Right -> println("First name: ${x.value}")
        is Either.Left -> println(InvalidFirstNameShow.show(x.value))
    }
}