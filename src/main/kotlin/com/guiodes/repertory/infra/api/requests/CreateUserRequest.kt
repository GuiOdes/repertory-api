package com.guiodes.repertory.infra.api.requests

import com.guiodes.repertory.domain.models.User

data class CreateUserRequest(
    val name: String,
    val isActive: Boolean,
    val email: String,
    val password: String
) {
    fun toDomain(
        encodedPassword: String
    ) = User(
        name = name,
        isActive = isActive,
        email = email,
        password = encodedPassword
    )
}
