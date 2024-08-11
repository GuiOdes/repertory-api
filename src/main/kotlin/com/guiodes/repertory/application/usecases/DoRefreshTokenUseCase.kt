package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.commons.toUUID
import com.guiodes.repertory.application.gateways.UserGateway
import com.guiodes.repertory.domain.api.responses.LoginResponse

class DoRefreshTokenUseCase(
    private val decodeJwtTokenUseCase: DecodeJwtTokenUseCase,
    private val buildJwtTokenUseCase: BuildJwtTokenUseCase,
    private val userGateway: UserGateway,
) {
    fun execute(token: String): LoginResponse {
        val jwt = decodeJwtTokenUseCase.execute(token)

        val user =
            userGateway.findById(jwt.subject.toUUID())
                ?: throw RuntimeException("User not found") // TODO: Create a custom exception

        return buildJwtTokenUseCase.execute(user)
    }
}
