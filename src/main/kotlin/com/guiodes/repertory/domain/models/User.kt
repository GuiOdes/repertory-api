package com.guiodes.repertory.domain.models

import com.guiodes.repertory.domain.interfaces.BaseEntity
import java.time.LocalDateTime
import java.util.UUID

data class User(
    override val id: UUID = UUID.randomUUID(),
    val name: String,
    val isActive: Boolean,
    val email: String,
    val password: String,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity()
