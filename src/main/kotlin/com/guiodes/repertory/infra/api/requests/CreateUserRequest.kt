package com.guiodes.repertory.infra.api.requests

import com.guiodes.repertory.domain.models.User
import java.util.*

data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String
) {
    fun toDomain(
        encodedPassword: String
    ) = User(
        id = UUID.randomUUID()  ,
        name = name,
        isActive = true,
        email = email,
        password = encodedPassword
    )
}
