package com.guiodes.repertory.domain.models

import java.time.LocalDateTime
import java.util.UUID

data class UserMinistry(
    val ministryId: UUID,
    val userId: UUID,
    val isAdmin: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
