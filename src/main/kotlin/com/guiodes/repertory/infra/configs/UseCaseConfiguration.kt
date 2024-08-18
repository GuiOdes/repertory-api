package com.guiodes.repertory.infra.configs

import com.guiodes.repertory.application.gateways.AuthorityGateway
import com.guiodes.repertory.application.gateways.InstrumentGateway
import com.guiodes.repertory.application.usecases.AuthenticateUserUseCase
import com.guiodes.repertory.application.usecases.BuildJwtTokenUseCase
import com.guiodes.repertory.application.usecases.CreateAuthorityUseCase
import com.guiodes.repertory.application.usecases.CreateInstrumentUseCase
import com.guiodes.repertory.application.usecases.CreateUserUseCase
import com.guiodes.repertory.application.usecases.DecodeJwtTokenUseCase
import com.guiodes.repertory.application.usecases.DoRefreshTokenUseCase
import com.guiodes.repertory.infra.database.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder

@Configuration
class UseCaseConfiguration {
    @Bean
    fun buildJwtTokenUseCase(
        jwtEncoder: JwtEncoder,
        @Value("\${security.jwt.expiration-seconds}") jwtExpiration: Long,
    ) = BuildJwtTokenUseCase(jwtEncoder, jwtExpiration)

    @Bean
    fun authenticateUserUseCase(
        userRepository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder,
        buildJwtTokenUseCase: BuildJwtTokenUseCase,
        authorityGateway: AuthorityGateway,
    ) = AuthenticateUserUseCase(userRepository, authorityGateway, passwordEncoder, buildJwtTokenUseCase)

    @Bean
    fun createUserUseCase(
        userRepository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder,
    ) = CreateUserUseCase(userRepository, passwordEncoder)

    @Bean
    fun decodeJwtTokenUseCase(jwtDecoder: JwtDecoder) = DecodeJwtTokenUseCase(jwtDecoder)

    @Bean
    fun doRefreshTokenUseCase(
        decodeJwtTokenUseCase: DecodeJwtTokenUseCase,
        buildJwtTokenUseCase: BuildJwtTokenUseCase,
        userRepository: UserRepository,
        authorityGateway: AuthorityGateway,
    ) = DoRefreshTokenUseCase(decodeJwtTokenUseCase, buildJwtTokenUseCase, userRepository, authorityGateway)

    @Bean
    fun createAuthorityUseCase(authorityGateway: AuthorityGateway) = CreateAuthorityUseCase(authorityGateway)

    @Bean
    fun createInstrumentUseCase(instrumentGateway: InstrumentGateway) = CreateInstrumentUseCase(instrumentGateway)
}
