package com.guiodes.repertory.domain.interfaces

import java.time.LocalDateTime
import java.util.UUID

abstract class BaseEntity(
    val id: UUID? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
