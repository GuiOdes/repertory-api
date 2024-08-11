package com.guiodes.repertory.domain.api.requests

import com.guiodes.repertory.domain.models.Authority
import java.time.LocalDateTime
import java.util.UUID

data class CreateAuthorityRequest(
    val name: String,
) {
    fun toDomain() =
        Authority(
            id = UUID.randomUUID(),
            name = name,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
}
