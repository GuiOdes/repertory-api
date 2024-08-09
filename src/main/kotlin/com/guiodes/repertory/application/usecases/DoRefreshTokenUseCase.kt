package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.commons.toUUID
import com.guiodes.repertory.application.repositories.UserRepository
import com.guiodes.repertory.infra.api.responses.LoginResponse

class DoRefreshTokenUseCase(
    private val decodeJwtTokenUseCase: DecodeJwtTokenUseCase,
    private val buildJwtTokenUseCase: BuildJwtTokenUseCase,
    private val userRepository: UserRepository,
) {
    fun execute(token: String): LoginResponse {
        val jwt = decodeJwtTokenUseCase.execute(token)

        val user =
            userRepository.findById(jwt.subject.toUUID())
                ?: throw RuntimeException("User not found") // TODO: Create a custom exception

        return buildJwtTokenUseCase.execute(user)
    }
}
