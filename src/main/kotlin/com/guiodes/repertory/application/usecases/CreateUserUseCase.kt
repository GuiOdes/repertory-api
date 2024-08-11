package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.UserGateway
import com.guiodes.repertory.domain.api.requests.CreateUserRequest
import com.guiodes.repertory.domain.models.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class CreateUserUseCase(
    private val repository: UserGateway,
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    fun execute(request: CreateUserRequest): User {
        val encodedPassword = passwordEncoder.encode(request.password)

        return repository.save(request.toDomain(encodedPassword))
    }
}
