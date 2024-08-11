package com.guiodes.repertory.configs

import io.mockk.clearAllMocks
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@AutoConfigureMockMvc
@ContextConfiguration(
    initializers = [
        TestContainersInitializer::class,
    ],
)
class IntegrationTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var flyway: Flyway

    @BeforeEach
    fun setUp() {
        flyway.apply {
            clean()
            migrate()
        }

        clearAllMocks()
    }
}
