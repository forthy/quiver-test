package richard.demo

import arrow.core.Option
import arrow.core.identity
import io.github.cdimascio.dotenv.dotenv
import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.annotations.QuarkusMain
import richard.demo.config.TCPPort
import richard.demo.models.FirstName
import richard.demo.models.InvalidFirstNameShow
import richard.demo.predicates.NonEmptyStringPredicate
import richard.demo.predicates.RegisteredTCPPortPredicate
import richard.demo.predicates.ShorterThanPredicate
import richard.demo.predicates.and
import richard.demo.repo.config.EnvConfigRepo

@QuarkusMain
class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // TODO - experiment about manually setting  Quarkus' HTTP port (current status: failed)
            // TODO - should change the app behaviour that using a port defined by 'QUIVER_HTTP_PORT' or 'quiver.http.port'
            // if any of these properties is found.

            // DEBUG
            println("Before config...")
            System.getProperties().forEach { m -> println("Key: ${m.key}, value: ${m.value}") }

            Option.fromNullable(System.getProperty("quarkus.http.port")).fold({
                EnvConfigRepo.readAs<Int> { dotenv() }("QUARKUS_HTTP_PORT").fold({
                    System.setProperty("quarkus.http.port", "8080")
                }, { p ->
                    TCPPort.ofWithPredicate(RegisteredTCPPortPredicate)(p).fold({ e ->
                        System.setProperty("quarkus.http.port", "8080")
                    }, { port -> System.setProperty("quarkus.http.port", port.v.toString()) })
                })
            }, { p -> if (p == "9000") System.setProperty("quarkus.http.port", "8080") else p })

            // DEBUG
            println("After config...")
            System.getProperties().forEach { m -> println("Key: ${m.key}, value: ${m.value}") }

            println("Hello World!")

            println(FirstName.of("Richard"))
            println(FirstName.ofWithPredicate(ShorterThanPredicate(6))("Richard"))

            val p = and(ShorterThanPredicate(6))(NonEmptyStringPredicate)
            FirstName.ofWithPredicate(p)("Tooshort").fold(
                { e -> println(InvalidFirstNameShow.show(e)) },
                { firstName -> println("First name: ${firstName.v}") }
            )

            Quarkus.run(*args)
        }
    }
}
