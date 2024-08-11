package com.guiodes.repertory.domain.api.requests

data class LoginRequest(
    val email: String,
    val password: String,
)
