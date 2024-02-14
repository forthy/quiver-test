package richard.demo.controllers

import arrow.core.Either
import arrow.core.Option
import org.http4k.core.Body
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import richard.demo.models.FirstName
import richard.demo.models.LastName
import richard.demo.models.MiddleName
import richard.demo.show.Show

data class AddPersonPayload(val firstName: String, val middleName: String?, val lastName: String)
data class AddPersonAction(val firstName: FirstName, val middleName: Option<MiddleName>, val lastName: LastName)
data class BodyParsingError(val errBody: Body, val throwable: Throwable)

private val bodyToStr: (Body) -> String = { b -> b.payload.array().toString(Charsets.UTF_8) }
val bodyParsingErrorShow = object : Show<BodyParsingError> {
    override fun show(v: BodyParsingError): String =
        v.throwable.message?.let { m -> "Parsing error: $m - body:\n'${bodyToStr(v.errBody)}'" }
            ?: "Parsing error - body:\n'${bodyToStr(v.errBody)}'"
}

private val bodyLens = Body.auto<AddPersonPayload>().toLens()

val addPersonRoute: RoutingHttpHandler = "/person" bind POST to { req ->
    Either.catch { bodyLens(req) }.mapLeft { t -> BodyParsingError(req.body, t) }.fold(
        { e -> Response(BAD_REQUEST).body(bodyParsingErrorShow.show(e)) },
        { r -> Response(OK).with(bodyLens of r) })

}