package org.example.richard.demo.models

import arrow.core.None
import arrow.core.Option

class FullName private constructor(val fn: FirstName, val ln: LastName, val mn: Option<MiddleName> = None) {
    companion object {
        fun of(mno: Option<MiddleName>): (FirstName) -> (LastName) -> FullName = { fn ->
            { ln -> FullName(fn, ln, mno) }
        }
    }
}