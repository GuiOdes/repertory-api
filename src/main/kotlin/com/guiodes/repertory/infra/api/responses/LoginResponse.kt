package com.guiodes.repertory.infra.api.responses

data class LoginResponse(
    val token: String,
    val expiresIn: Long
)
