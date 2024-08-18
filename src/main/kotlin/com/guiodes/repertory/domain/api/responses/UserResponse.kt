package com.guiodes.repertory.domain.api.responses

import com.guiodes.repertory.domain.interfaces.BaseEntity
import com.guiodes.repertory.domain.models.Authority
import com.guiodes.repertory.domain.models.Instrument
import java.time.LocalDateTime
import java.util.UUID

data class UserResponse(
    override val id: UUID = UUID.randomUUID(),
    val name: String,
    val isActive: Boolean,
    val email: String,
    val password: String,
    val instruments: List<Instrument> = emptyList(),
    val authorities: List<Authority> = emptyList(),
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity()
