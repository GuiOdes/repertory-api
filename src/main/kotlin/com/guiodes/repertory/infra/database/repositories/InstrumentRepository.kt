package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.application.gateways.InstrumentGateway
import com.guiodes.repertory.domain.models.Instrument
import com.guiodes.repertory.infra.database.addCondition
import com.guiodes.repertory.infra.database.expressions.InstrumentExpressions
import com.guiodes.repertory.infra.database.expressions.InstrumentExpressions.CREATE
import com.guiodes.repertory.infra.database.expressions.InstrumentExpressions.EXISTS
import com.guiodes.repertory.infra.database.expressions.InstrumentExpressions.FIND
import com.guiodes.repertory.infra.database.expressions.InstrumentExpressions.ID
import com.guiodes.repertory.infra.database.expressions.InstrumentExpressions.NAME
import com.guiodes.repertory.infra.database.expressions.InstrumentExpressions.UPDATE
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.UUID

@Repository
class InstrumentRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
): InstrumentGateway {

    override fun existsById(id: UUID): Boolean {
        val parameters = MapSqlParameterSource()
            .addValue("id", id)

        return jdbcTemplate.queryForObject(
            EXISTS.addCondition(ID),
            parameters,
            Boolean::class.java
        )!!
    }

    override fun findByNameContaining(name: String): List<Instrument> {
        val parameters = MapSqlParameterSource()
            .addValue("name", name)

        return jdbcTemplate.query(
            FIND.addCondition(NAME),
            parameters,
            rowMapper()
        )
    }

    override fun save(entity: Instrument): Instrument {
        val parameters = MapSqlParameterSource()
            .addValue("id", entity.id)
            .addValue("name", entity.name)
            .addValue("createdAt", entity.createdAt)
            .addValue("updatedAt", entity.updatedAt)

        jdbcTemplate.update(
            CREATE,
            parameters
        )

        return entity
    }

    override fun update(entity: Instrument): Instrument {
        val parameters = MapSqlParameterSource()
            .addValue("id", entity.id)
            .addValue("name", entity.name)
            .addValue("updatedAt", entity.updatedAt)

        jdbcTemplate.update(
            UPDATE,
            parameters
        )

        return entity
    }

    override fun delete(entity: Instrument) {
        val parameters = MapSqlParameterSource()
            .addValue("id", entity.id)

        jdbcTemplate.update(
            InstrumentExpressions.DELETE,
            parameters
        )
    }

    override fun findById(id: UUID): Instrument? {
        val parameters = MapSqlParameterSource()
            .addValue("id", id)

        return jdbcTemplate.query(
            FIND,
            parameters,
            rowMapper()
        ).firstOrNull()
    }

    private fun rowMapper(): (ResultSet, Int) -> Instrument = { rs, _ ->
        Instrument(
            id = UUID.fromString(rs.getString("id")),
            name = rs.getString("name"),
            createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
            updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
        )
    }
}