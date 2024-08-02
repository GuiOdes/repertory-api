package com.guiodes.repertory.configs

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class TestContainersInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val postgresContainer = CustomPostgresContainer()

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        postgresContainer.configure(applicationContext)
    }
}
