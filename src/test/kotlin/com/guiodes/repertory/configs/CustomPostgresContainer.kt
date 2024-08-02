package com.guiodes.repertory.configs

import org.flywaydb.core.Flyway
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

class CustomPostgresContainer :
    PostgreSQLContainer<CustomPostgresContainer>("postgres:15-alpine"),
    IntegrationTestsConfiguration {
    init {
        withUsername("root")
        withPassword("root")
        withDatabaseName("repertory")

        start()

        Flyway
            .configure()
            .dataSource(jdbcUrl, username, password)
            .locations("classpath:db/migration", "classpath:db/scripts")
            .load()
            .migrate()
    }

    override fun configure(applicationContext: ConfigurableApplicationContext) {
        TestPropertyValues.of("spring.datasource.url=${this.jdbcUrl}").applyTo(applicationContext)
    }
}
