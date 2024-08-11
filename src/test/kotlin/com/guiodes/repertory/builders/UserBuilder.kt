package com.guiodes.repertory.builders

import com.guiodes.repertory.domain.api.requests.CreateUserRequest
import com.guiodes.repertory.domain.models.Authority
import com.guiodes.repertory.domain.models.Instrument
import com.guiodes.repertory.domain.models.User
import java.time.LocalDateTime
import java.util.UUID

class UserBuilder : BaseEntityBuilder<User> {
    private val id: UUID = UUID.randomUUID()
    private val name: String = "name"
    private val isActive: Boolean = true
    private val email: String = "email@email.com"
    private val password: String = "password"
    private val instruments: List<Instrument> = emptyList()
    private val authorities: List<Authority> = emptyList()
    private val createdAt: LocalDateTime = LocalDateTime.now()
    private val updatedAt: LocalDateTime = LocalDateTime.now()

    fun buildRequest(
        name: String = "name",
        email: String = "user@user.com",
        password: String = "password",
    ) = CreateUserRequest(
        name = name,
        email = email,
        password = password,
    )

    override fun build() =
        User(
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
