package com.guiodes.repertory.configs

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class TestContainersInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val postgresContainer = CustomPostgresContainer()

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        postgresContainer.configure(applicationContext)

        System.setProperty("spring.datasource.url", postgresContainer.jdbcUrl)
        System.setProperty("spring.datasource.username", postgresContainer.username)
        System.setProperty("spring.datasource.password", postgresContainer.password)
    }
}
