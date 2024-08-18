package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.commons.toUUID
import com.guiodes.repertory.application.exceptions.NotFoundException
import com.guiodes.repertory.application.gateways.AuthorityGateway
import com.guiodes.repertory.application.gateways.UserGateway
import com.guiodes.repertory.domain.api.responses.LoginResponse
import com.guiodes.repertory.domain.models.User

class DoRefreshTokenUseCase(
    private val decodeJwtTokenUseCase: DecodeJwtTokenUseCase,
    private val buildJwtTokenUseCase: BuildJwtTokenUseCase,
    private val userGateway: UserGateway,
    private val authorityGateway: AuthorityGateway,
) {
    fun execute(token: String): LoginResponse {
        val jwt = decodeJwtTokenUseCase.execute(token)

        val user =
            userGateway.findById(jwt.subject.toUUID())
                ?: throw NotFoundException(User::class)

        val authorities = authorityGateway.findByUserId(user.id)

        return buildJwtTokenUseCase.execute(user, authorities)
    }
}
