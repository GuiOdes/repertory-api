package com.guiodes.repertory.infra.database

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UtilsTest {
    @Test
    fun `should add condition with AND when there is a WHERE clause`() {
        val sql = "SELECT * FROM table WHERE column = 1"
        val condition = "column = 2"
        val result = sql.addCondition(condition)

        assertThat(result).isEqualTo("SELECT * FROM table WHERE column = 1 AND column = 2")
    }

    @Test
    fun `should add condition with WHERE when there is no WHERE clause`() {
        val sql = "SELECT * FROM table"
        val condition = "column = 2"
        val result = sql.addCondition(condition)

        assertThat(result).isEqualTo("SELECT * FROM table WHERE column = 2")
    }
}
