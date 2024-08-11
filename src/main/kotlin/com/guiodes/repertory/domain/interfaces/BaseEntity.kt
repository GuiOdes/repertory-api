package com.guiodes.repertory.domain.interfaces

import java.time.LocalDateTime
import java.util.UUID

abstract class BaseEntity(
    open val id: UUID = UUID.randomUUID(),
    open val createdAt: LocalDateTime = LocalDateTime.now(),
    open var updatedAt: LocalDateTime = LocalDateTime.now(),
)
