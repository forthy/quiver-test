package richard.demo.config

import arrow.core.Either
import arrow.core.Predicate
import arrow.core.raise.either
import arrow.core.raise.ensure

@JvmInline
value class IP private constructor(val v: String) {
    companion object {
        fun ofWithPredicate(p: Predicate<String>): (String) -> Either<InvalidIP, IP> = { v ->
            either {
                ensure(p(v)) { InvalidIP(v) }
                IP(v)
            }
        }
    }
}

@JvmInline
value class TCPPort private constructor(val v: Int) {
    companion object {
        fun ofWithPredicate(p: Predicate<Int>): (Int) -> Either<InvalidTCPPort, TCPPort> = { v ->
            either {
                ensure(p(v)) { InvalidTCPPort(v) }
                TCPPort(v)
            }
        }
    }
}

data class ServiceAddress(val ip: IP, val port: TCPPort)