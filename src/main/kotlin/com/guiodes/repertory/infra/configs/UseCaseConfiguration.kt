package com.guiodes.repertory.infra.configs

import com.guiodes.repertory.application.usecases.AuthenticateUserUseCase
import com.guiodes.repertory.application.usecases.CreateUserUseCase
import com.guiodes.repertory.infra.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtEncoder

@Configuration
class UseCaseConfiguration {

    @Bean
    fun authenticateUserUseCase(
        userRepository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder,
        @Value("\${security.jwt.expiration-seconds}")
        jwtExpiration: Long,
        jwtEncoder: JwtEncoder
    ) = AuthenticateUserUseCase(userRepository, passwordEncoder, jwtExpiration, jwtEncoder)

    @Bean
    fun createUserUseCase(
        userRepository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder
    ) = CreateUserUseCase(userRepository, passwordEncoder)
}