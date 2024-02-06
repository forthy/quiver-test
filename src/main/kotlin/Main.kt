package org.example

import org.example.richard.demo.models.FirstName
import org.example.richard.demo.models.InvalidFirstNameShow
import org.example.richard.demo.predicates.NonEmptyStringPredicate
import org.example.richard.demo.predicates.ShorterThanPredicate
import org.example.richard.demo.predicates.and

fun main(args: Array<String>) {
    println("Hello World!")

    println(FirstName.of("Richard"))
    println(FirstName.ofWithPredicate(ShorterThanPredicate(6))("Richard"))

    val p = and(ShorterThanPredicate(6))(NonEmptyStringPredicate)
    FirstName.ofWithPredicate(p)("Tooshort").fold(
        { e -> println(InvalidFirstNameShow.show(e)) },
        { firstName -> println("First name: ${firstName.v}") }
    )
}