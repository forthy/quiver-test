package richard.demo.config

sealed interface ConfigError

data class InvalidDBUri(val invalidUri: String) : ConfigError
data class InvalidIP(val invalidIP: String): ConfigError
data class InvalidTCPPort(val invalidPort: Int) : ConfigError
data class ConfigNotDefined(val configKey: String): ConfigError