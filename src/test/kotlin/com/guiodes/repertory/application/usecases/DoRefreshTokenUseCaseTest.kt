package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.exceptions.NotFoundException
import com.guiodes.repertory.application.gateways.AuthorityGateway
import com.guiodes.repertory.builders.AuthorityBuilder
import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.configs.UnitTest
import com.guiodes.repertory.domain.api.responses.LoginResponse
import com.guiodes.repertory.infra.database.repositories.UserRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.security.oauth2.jwt.Jwt

class DoRefreshTokenUseCaseTest(
    @MockK private val decodeJwtTokenUseCase: DecodeJwtTokenUseCase,
    @MockK private val buildJwtTokenUseCase: BuildJwtTokenUseCase,
    @MockK private val userRepository: UserRepository,
    @MockK private val authorityGateway: AuthorityGateway,
) : UnitTest() {
    @InjectMockKs
    private lateinit var doRefreshTokenUseCase: DoRefreshTokenUseCase

    private val token = "token"
    private val user = UserBuilder().build()
    private val authorities = listOf(AuthorityBuilder().build())
    private val jwt =
        Jwt
            .withTokenValue(token)
            .header("alg", "HS256")
            .header("typ", "JWT")
            .subject(user.id.toString())
            .build()

    @Test
    fun `Should do refresh token with success`() {
        every { decodeJwtTokenUseCase.execute(token) } returns jwt
        every { userRepository.findById(user.id) } returns user
        every { buildJwtTokenUseCase.execute(user, authorities) } returns
            LoginResponse(
                token = "newToken",
                refreshToken = "newRefreshToken",
                expiresIn = 3600,
            )
        every { authorityGateway.findByUserId(user.id) } returns authorities

        val response = doRefreshTokenUseCase.execute(token)

        assertThat(response.token).isEqualTo("newToken")
        assertThat(response.refreshToken).isEqualTo("newRefreshToken")
        assertThat(response.expiresIn).isEqualTo(3600)

        verify(exactly = 1) { decodeJwtTokenUseCase.execute(token) }
        verify(exactly = 1) { userRepository.findById(user.id) }
        verify(exactly = 1) { buildJwtTokenUseCase.execute(user, authorities) }
        verify(exactly = 1) { authorityGateway.findByUserId(user.id) }
    }

    @Test
    fun `Should throw exception when user not found`() {
        every { decodeJwtTokenUseCase.execute(token) } returns jwt
        every { userRepository.findById(user.id) } returns null

        assertThatThrownBy { doRefreshTokenUseCase.execute(token) }
            .isInstanceOf(NotFoundException::class.java)
            .hasMessage("User not found")

        verify(exactly = 1) { decodeJwtTokenUseCase.execute(token) }
        verify(exactly = 1) { userRepository.findById(user.id) }
    }
}
