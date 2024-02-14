package richard.demo.controllers

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

fun App(): HttpHandler {
    return routes(helloRoutingHttpHandler, addPersonRoute)
}

val helloRoutingHttpHandler: RoutingHttpHandler =  "/hello" bind GET to { Response(OK).body("hello quarkus world!") }