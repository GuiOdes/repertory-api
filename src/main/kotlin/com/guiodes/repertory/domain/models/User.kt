package com.guiodes.repertory.domain.models

import com.guiodes.repertory.domain.interfaces.BaseEntity
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.UUID

data class User(
    override val id: UUID? = null,
    val name: String,
    val isActive: Boolean,
    val email: String,
    val password: String,
    val instruments: List<Instrument> = emptyList(),
    val authorities: List<Authority> = emptyList(),
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity()
