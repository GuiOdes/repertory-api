package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.exceptions.NotFoundException
import com.guiodes.repertory.application.repositories.UserRepository
import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.configs.UnitTest
import com.guiodes.repertory.infra.api.requests.LoginRequest
import com.guiodes.repertory.infra.api.responses.LoginResponse
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AuthenticateUserUseCaseTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val passwordEncoder: BCryptPasswordEncoder,
    @MockK private val buildJwtTokenUseCase: BuildJwtTokenUseCase
) : UnitTest() {

    @InjectMockKs
    private lateinit var authenticateUserUseCase: AuthenticateUserUseCase

    @Test
    fun `should authenticate user`() {
        val user = UserBuilder().build()
        val loginRequest = LoginRequest(
            email = user.email,
            password = "password"
        )

        every { userRepository.findByEmail(loginRequest.email) } returns user
        every { passwordEncoder.matches(loginRequest.password, user.password) } returns true
        every { buildJwtTokenUseCase.execute(user) } returns LoginResponse(
            token = "token",
            refreshToken = "refreshToken",
            expiresIn = 3600
        )

        authenticateUserUseCase.execute(loginRequest)

        verify(exactly = 1) { userRepository.findByEmail(loginRequest.email) }
        verify(exactly = 1) { passwordEncoder.matches(loginRequest.password, user.password) }
        verify(exactly = 1) { buildJwtTokenUseCase.execute(user) }
    }

    @Test
    fun `should throw exception when user not found`() {
        val loginRequest = LoginRequest(
            email = "email",
            password = "password"
        )

        every { userRepository.findByEmail(loginRequest.email) } returns null

        assertThrows<NotFoundException> {
            authenticateUserUseCase.execute(loginRequest)
        }

        verify(exactly = 1) { userRepository.findByEmail(loginRequest.email) }
    }

    @Test
    fun `should throw exception when password is invalid`() {
        val user = UserBuilder().build()
        val loginRequest = LoginRequest(
            email = user.email,
            password = "password"
        )

        every { userRepository.findByEmail(loginRequest.email) } returns user
        every { passwordEncoder.matches(loginRequest.password, user.password) } returns false

        assertThrows<BadCredentialsException> {
            authenticateUserUseCase.execute(loginRequest)
        }

        verify(exactly = 1) { userRepository.findByEmail(loginRequest.email) }
        verify(exactly = 1) { passwordEncoder.matches(loginRequest.password, user.password) }
    }
}
