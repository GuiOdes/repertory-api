package com.guiodes.repertory.builders

import com.guiodes.repertory.domain.models.Authority
import com.guiodes.repertory.domain.models.Instrument
import com.guiodes.repertory.domain.models.User
import java.time.LocalDateTime
import java.util.UUID

object UserBuilder {
    fun build(
        id: UUID = UUID.randomUUID(),
        name: String = "name",
        isActive: Boolean = true,
        email: String = "email@email.com",
        password: String = "password",
        instruments: List<Instrument> = emptyList(),
        authorities: List<Authority> = emptyList(),
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
    ) = User(
        id = id,
        name = name,
        isActive = isActive,
        email = email,
        password = password,
        instruments = instruments,
        authorities = authorities,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
