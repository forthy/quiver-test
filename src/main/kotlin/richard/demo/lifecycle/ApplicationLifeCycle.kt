package richard.demo.lifecycle

import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import org.jboss.logging.Logger

@ApplicationScoped
class ApplicationLifeCycle {
    private val logger = Logger.getLogger(ApplicationLifeCycle::class.java)

    fun onStart(@Observes ev: StartupEvent) {
        logger.info("QuiverTest has started\nStartupEvent: $ev")
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        logger.info("QuiverTest is stopping...\nShutdownEvent: $ev")
    }
}