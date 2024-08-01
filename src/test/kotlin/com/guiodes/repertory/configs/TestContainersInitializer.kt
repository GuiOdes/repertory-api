package com.guiodes.repertory.configs

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName
import java.lang.System.setProperty

class TestContainersInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val postgresContainer = CustomPostgresContainer()
    private val redisContainer =
        GenericContainer(
            DockerImageName.parse("redis:6.2.5-alpine"),
        ).run {
            withExposedPorts(6379)
            withEnv("REDIS_PASSWORD", "root")
        }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        postgresContainer.configure(applicationContext)
        redisContainer.start()
        setProperty("spring.redis.host", redisContainer.host)
        setProperty("spring.redis.port", redisContainer.getMappedPort(6379).toString())
        setProperty("spring.redis.password", "root")
    }
}
