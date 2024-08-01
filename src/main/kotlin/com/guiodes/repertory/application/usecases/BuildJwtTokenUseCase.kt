package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.infra.api.responses.LoginResponse
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import java.time.Instant

class BuildJwtTokenUseCase(
    private val jwtEncoder: JwtEncoder,
    private val jwtExpiration: Long,
) {
    fun execute(user: User): LoginResponse {
        val now = Instant.now()

        val claims =
            JwtClaimsSet
                .builder()
                .issuer("repertory")
                .subject(user.id.toString())
                .claim("email", user.email)
                .claim("name", user.name)
                .claim("authorities", user.authorities)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(jwtExpiration))
                .build()

        val jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue

        val refreshTokenClaims =
            JwtClaimsSet
                .builder()
                .issuer("repertory")
                .subject(user.id.toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(jwtExpiration * 2))
                .build()

        val refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaims)).tokenValue

        return LoginResponse(token = jwt, expiresIn = jwtExpiration, refreshToken = refreshToken)
    }
}
