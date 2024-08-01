package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.repositories.UserRepository
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.infra.api.requests.CreateUserRequest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class CreateUserUseCase(
    private val repository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    fun execute(request: CreateUserRequest): User {
        val encodedPassword = passwordEncoder.encode(request.password)

        return repository.save(request.toDomain(encodedPassword))
    }
}
