package com.guiodes.repertory.domain.api.responses

data class LoginResponse(
    val token: String,
    val refreshToken: String,
    val expiresIn: Long,
)
