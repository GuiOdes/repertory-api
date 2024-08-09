package com.guiodes.repertory.application.usecases

import org.springframework.security.oauth2.jwt.JwtDecoder

class DecodeJwtTokenUseCase(
    private val jwtDecoder: JwtDecoder,
) {
    fun execute(token: String) = jwtDecoder.decode(token.substring(7))
}
