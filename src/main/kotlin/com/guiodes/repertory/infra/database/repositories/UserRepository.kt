package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.application.gateways.UserGateway
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.infra.database.addCondition
import com.guiodes.repertory.infra.database.expressions.UserExpressions
import com.guiodes.repertory.infra.database.expressions.UserExpressions.EXISTS
import com.guiodes.repertory.infra.database.expressions.UserExpressions.ID
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.UUID

@Repository
class UserRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) : UserGateway {
    override fun findByEmail(email: String): User? {
        val parameters =
            MapSqlParameterSource()
                .addValue("email", email)

        return jdbcTemplate
            .query(
                UserExpressions.FIND.addCondition(UserExpressions.EMAIL),
                parameters,
                rowMapper(),
            ).firstOrNull()
    }

    override fun existsById(id: UUID): Boolean {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", id)

        return jdbcTemplate.queryForObject(
            EXISTS.addCondition(ID),
            parameters,
            Boolean::class.java,
        )!!
    }

    override fun save(entity: User): User {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", entity.id)
                .addValue("name", entity.name)
                .addValue("email", entity.email)
                .addValue("password", entity.password)
                .addValue("isActive", entity.isActive)
                .addValue("createdAt", entity.createdAt)
                .addValue("updatedAt", entity.updatedAt)

        jdbcTemplate.update(UserExpressions.SAVE, parameters)

        return entity
    }

    override fun update(entity: User): User {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", entity.id)
                .addValue("name", entity.name)
                .addValue("email", entity.email)
                .addValue("password", entity.password)
                .addValue("isActive", entity.isActive)
                .addValue("updatedAt", entity.updatedAt)

        jdbcTemplate.update(UserExpressions.UPDATE, parameters)

        return entity
    }

    override fun deleteById(id: UUID) {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", id)

        jdbcTemplate.update(UserExpressions.SOFT_DELETE, parameters)
    }

    override fun restoreById(id: UUID) {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", id)

        jdbcTemplate.update(UserExpressions.RESTORE, parameters)
    }

    override fun findById(id: UUID): User? {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", id)

        return jdbcTemplate
            .query(
                UserExpressions.FIND.addCondition(ID),
                parameters,
                rowMapper(),
            ).firstOrNull()
    }

    private fun rowMapper(): (ResultSet, Int) -> User =
        { rs, _ ->
            User(
                id = UUID.fromString(rs.getString("ID")),
                name = rs.getString("NAME"),
                email = rs.getString("EMAIL"),
                isActive = rs.getBoolean("IS_ACTIVE"),
                password = rs.getString("PASSWORD"),
                createdAt = rs.getTimestamp("CREATED_AT").toLocalDateTime(),
                updatedAt = rs.getTimestamp("UPDATED_AT").toLocalDateTime(),
            )
        }
}
