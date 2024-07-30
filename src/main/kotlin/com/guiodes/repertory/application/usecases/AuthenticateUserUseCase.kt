package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.repositories.UserRepository
import com.guiodes.repertory.infra.api.requests.LoginRequest
import com.guiodes.repertory.infra.api.responses.LoginResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import java.time.Instant

class AuthenticateUserUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    @Value("\${security.jwt.expiration-seconds}")
    private val jwtExpiration: Long,
    private val jwtEncoder: JwtEncoder
) {

    fun execute(loginRequest: LoginRequest): LoginResponse {
        val user = userRepository.findByEmailAndPassword(
            loginRequest.email,
            passwordEncoder.encode(loginRequest.password)
        ) ?: throw BadCredentialsException("Invalid credentials")

        val now = Instant.now()

        val claims = JwtClaimsSet.builder()
            .issuer("repertory")
            .subject(user.id.toString())
            .claim("email", user.email)
            .claim("name", user.name)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(jwtExpiration))
            .build()

        val jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue

        return LoginResponse(jwt, jwtExpiration)
    }
}
