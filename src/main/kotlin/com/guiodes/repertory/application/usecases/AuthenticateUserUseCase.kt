package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.repositories.UserRepository
import com.guiodes.repertory.infra.api.requests.LoginRequest
import com.guiodes.repertory.infra.api.responses.LoginResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AuthenticateUserUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val buildJwtTokenUseCase: BuildJwtTokenUseCase,
) {
    fun execute(loginRequest: LoginRequest): LoginResponse {
        val user =
            userRepository.findByEmail(loginRequest.email)
                ?: throw RuntimeException("User not found")

        if (!passwordEncoder.matches(
                loginRequest.password,
                user.password,
            )
        ) {
            throw BadCredentialsException("Invalid credentials")
        }

        return buildJwtTokenUseCase.execute(user)
    }
}
