package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.exceptions.NotFoundException
import com.guiodes.repertory.application.gateways.AuthorityGateway
import com.guiodes.repertory.application.gateways.UserGateway
import com.guiodes.repertory.domain.api.requests.LoginRequest
import com.guiodes.repertory.domain.api.responses.LoginResponse
import com.guiodes.repertory.domain.models.User
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AuthenticateUserUseCase(
    private val userGateway: UserGateway,
    private val authorityGateway: AuthorityGateway,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val buildJwtTokenUseCase: BuildJwtTokenUseCase,
) {
    fun execute(loginRequest: LoginRequest): LoginResponse {
        val user =
            userGateway.findByEmail(loginRequest.email)
                ?: throw NotFoundException(User::class)

        if (!passwordEncoder.matches(
                loginRequest.password,
                user.password,
            )
        ) {
            throw BadCredentialsException("Invalid credentials")
        }

        val authorities = authorityGateway.findByUserId(user.id)

        return buildJwtTokenUseCase.execute(user, authorities)
    }
}
