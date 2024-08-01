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
    private val password: String,
    val instruments: List<Instrument> = emptyList(),
    private val authorities: List<Authority> = emptyList(),
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity(),
    UserDetails {
    override fun getAuthorities() = authorities

    override fun getPassword() = password

    override fun getUsername() = email
}
