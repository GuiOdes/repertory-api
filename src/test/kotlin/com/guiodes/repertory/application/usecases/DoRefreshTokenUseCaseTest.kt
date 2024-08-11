package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.exceptions.NotFoundException
import com.guiodes.repertory.application.repositories.UserRepository
import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.configs.UnitTest
import com.guiodes.repertory.infra.api.responses.LoginResponse
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.security.oauth2.jwt.Jwt

class DoRefreshTokenUseCaseTest(
    @MockK private val decodeJwtTokenUseCase: DecodeJwtTokenUseCase,
    @MockK private val buildJwtTokenUseCase: BuildJwtTokenUseCase,
    @MockK private val userRepository: UserRepository
) : UnitTest() {

    @InjectMockKs
    private lateinit var doRefreshTokenUseCase: DoRefreshTokenUseCase

    private val token = "token"
    private val user = UserBuilder().build()
    private val jwt = Jwt.withTokenValue(token).header("alg", "HS256")
        .header("typ", "JWT").subject(user.id.toString()).build()

    @Test
    fun `Should do refresh token with success`() {
        every { decodeJwtTokenUseCase.execute(token) } returns jwt
        every { userRepository.findById(user.id!!) } returns user
        every { buildJwtTokenUseCase.execute(user) } returns LoginResponse(
            token = "newToken",
            refreshToken = "newRefreshToken",
            expiresIn = 3600
        )

        val response = doRefreshTokenUseCase.execute(token)

        assertThat(response.token).isEqualTo("newToken")
        assertThat(response.refreshToken).isEqualTo("newRefreshToken")
        assertThat(response.expiresIn).isEqualTo(3600)
    }

    @Test
    fun `Should throw exception when user not found`() {
        every { decodeJwtTokenUseCase.execute(token) } returns jwt
        every { userRepository.findById(user.id!!) } returns null

        assertThatThrownBy { doRefreshTokenUseCase.execute(token) }
            .isInstanceOf(NotFoundException::class.java)
            .hasMessage("User not found")
    }
}