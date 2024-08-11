package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.application.gateways.AuthorityGateway
import com.guiodes.repertory.domain.models.Authority
import com.guiodes.repertory.infra.database.addCondition
import com.guiodes.repertory.infra.database.expressions.AuthorityExpressions.CREATE
import com.guiodes.repertory.infra.database.expressions.AuthorityExpressions.DELETE
import com.guiodes.repertory.infra.database.expressions.AuthorityExpressions.FIND
import com.guiodes.repertory.infra.database.expressions.AuthorityExpressions.ID
import com.guiodes.repertory.infra.database.expressions.AuthorityExpressions.UPDATE
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.UUID

@Repository
class AuthorityRepository(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate,
) : AuthorityGateway {
    override fun save(entity: Authority): Authority {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", entity.id)
                .addValue("name", entity.name)
                .addValue("createdAt", entity.createdAt)
                .addValue("updatedAt", entity.updatedAt)

        namedParameterJdbcTemplate.update(
            CREATE,
            parameters,
        )

        return entity
    }

    override fun update(entity: Authority): Authority {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", entity.id)
                .addValue("name", entity.name)
                .addValue("updatedAt", entity.updatedAt)

        namedParameterJdbcTemplate.update(
            UPDATE,
            parameters,
        )

        return entity
    }

    override fun delete(entity: Authority) {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", entity.id)

        namedParameterJdbcTemplate.update(
            DELETE,
            parameters,
        )
    }

    override fun findById(id: UUID): Authority? {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", id)

        return namedParameterJdbcTemplate
            .query(
                FIND.addCondition(ID),
                parameters,
                rowMapper(),
            ).firstOrNull()
    }

    override fun findAll(): List<Authority> =
        namedParameterJdbcTemplate.query(
            FIND,
            rowMapper(),
        )

    fun rowMapper(): (ResultSet, Int) -> Authority =
        { rs, _ ->
            Authority(
                id = UUID.fromString(rs.getString("id")),
                name = rs.getString("name"),
                createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at").toLocalDateTime(),
            )
        }
}
