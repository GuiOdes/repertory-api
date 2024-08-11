package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.configs.UnitTest
import com.guiodes.repertory.domain.api.requests.CreateUserRequest
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.infra.database.repositories.UserRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class CreateUserUseCaseTest(
    @MockK private val repository: UserRepository,
    @MockK private val passwordEncoder: BCryptPasswordEncoder,
) : UnitTest() {
    @InjectMockKs
    private lateinit var createUserUseCase: CreateUserUseCase

    @Test
    fun `should create user`() {
        val request =
            CreateUserRequest(
                email = "email",
                name = "name",
                password = "password",
            )
        val user = slot<User>()

        every { passwordEncoder.encode(request.password) } returns "encodedPassword"
        every { repository.save(capture(user)) } returns UserBuilder().build()

        createUserUseCase.execute(request)

        verify(exactly = 1) { passwordEncoder.encode(request.password) }
        verify(exactly = 1) { repository.save(user.captured) }
        user.captured.apply {
            assertEquals(request.email, email)
            assertEquals(request.name, name)
            assertEquals("encodedPassword", password)
        }
    }
}
