package com.guiodes.repertory.domain.models

import com.guiodes.repertory.domain.interfaces.BaseEntity
import java.time.LocalDateTime
import java.util.UUID

data class WorshipMinistry(
    val name: String,
    val users: List<UserMinistry>,
) : BaseEntity() {
    data class UserMinistry(
        val userId: UUID,
        val isAdmin: Boolean,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    )
}
