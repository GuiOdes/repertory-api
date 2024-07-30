package com.guiodes.repertory.domain.models

import com.guiodes.repertory.domain.interfaces.BaseEntity
import org.springframework.security.core.userdetails.UserDetails

data class User(
    val name: String,
    val isActive: Boolean,
    val email: String,
    private val password: String,
    val instruments: List<Instrument> = emptyList(),
    private val authorities: List<Authority> = emptyList()
): BaseEntity(), UserDetails {
    override fun getAuthorities() = authorities

    override fun getPassword() = password

    override fun getUsername() = email
}
