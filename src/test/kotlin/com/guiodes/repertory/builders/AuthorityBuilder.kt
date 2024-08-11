package com.guiodes.repertory.builders

import com.guiodes.repertory.domain.models.Authority
import java.time.LocalDateTime
import java.util.UUID

class AuthorityBuilder : BaseEntityBuilder<Authority> {
    private val id: UUID = UUID.randomUUID()
    private val name: String = "name"
    private val createdAt: LocalDateTime = LocalDateTime.now()
    private val updatedAt: LocalDateTime = LocalDateTime.now()

    override fun build() =
        Authority(
            id = id,
            name = name,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
}
