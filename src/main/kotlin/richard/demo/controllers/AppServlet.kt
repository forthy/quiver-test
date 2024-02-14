package richard.demo.controllers

import jakarta.servlet.Servlet
import jakarta.servlet.annotation.WebServlet
import org.http4k.servlet.jakarta.HttpHandlerServlet

@WebServlet("/")
class AppServlet : Servlet by HttpHandlerServlet(App())