package com.guiodes.repertory.infra.repositories

import com.guiodes.repertory.application.repositories.UserRepository
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.infra.database.addCondition
import com.guiodes.repertory.infra.database.expressions.UserExpressions
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.UUID

@Repository
class UserRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) : UserRepository {
    override fun findByEmail(email: String): User? {
        val parameters =
            MapSqlParameterSource()
                .addValue("email", email)

        return jdbcTemplate.query(
            UserExpressions.FIND.addCondition(UserExpressions.EMAIL),
            parameters,
            rowMapper(),
        ).firstOrNull()
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

    override fun delete(entity: User) {
        val parameters =
            MapSqlParameterSource()
                .addValue("id", entity.id)

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

        return jdbcTemplate.query(
            UserExpressions.FIND.addCondition(UserExpressions.ID),
            parameters,
            rowMapper(),
        ).firstOrNull()
    }

    override fun findAll(): List<User> = jdbcTemplate.query(UserExpressions.FIND, rowMapper())

    private fun rowMapper(): (rs: ResultSet, rowNum: Int) -> User =
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
