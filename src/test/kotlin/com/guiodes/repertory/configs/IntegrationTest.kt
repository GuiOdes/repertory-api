package com.guiodes.repertory.configs

import io.mockk.clearAllMocks
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

const val TRUNCATE_TABLES = """
    DO
    ${'$'}do${'$'}
    BEGIN
       EXECUTE
       COALESCE((SELECT 'TRUNCATE ' || string_agg(relname, ', ') || ' CASCADE'
        FROM pg_catalog.pg_statio_user_tables
        WHERE schemaname = 'public'
        AND pg_relation_size(relid) > 0
        AND relname <> 'flyway_schema_history'
       ), 'SELECT 1');
    END
    ${'$'}do${'$'};
"""

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

    @BeforeEach
    fun setUp() {
        jdbcTemplate.update(TRUNCATE_TABLES)
        clearAllMocks()
    }
}
