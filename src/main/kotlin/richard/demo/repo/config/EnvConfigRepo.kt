package richard.demo.repo.config

import arrow.core.Option
import arrow.core.some
import io.github.cdimascio.dotenv.Dotenv

object EnvConfigRepo {
    inline fun <reified T> readAs(crossinline env: () -> Dotenv): (String) -> Option<T> = { k ->
        env().let { dotenv ->
            Option.fromNullable(dotenv[k]).flatMap { s ->
                when (T::class) {
                    Int::class -> Option.catch { s.toInt() as T }
                    Long::class -> Option.catch { s.toLong() as T }
                    else -> (s as T).some()
                }
            }
        }
    }
}