package com.guiodes.repertory.infra.api.controllers

import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.configs.IntegrationTest
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.infra.api.requests.CreateUserRequest
import com.guiodes.repertory.infra.repositories.UserRepository
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class UserControllerIT(
    @Autowired private val testRestTemplate: TestRestTemplate,
    @Autowired private val userRepository: UserRepository
): IntegrationTest() {
    private val validCreateUserRequest = UserBuilder().buildRequest()

    @Test
    fun `Should create user with success`() {
        assertThat(userRepository.findByEmail(validCreateUserRequest.email)).isNull()

        val response = testRestTemplate.postForEntity("/user/new", validCreateUserRequest, User::class.java)

        assertThat(userRepository.findByEmail(validCreateUserRequest.email)).isNotNull()

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        response.body.also {
            assertThat(it!!.id).isNotNull
            assertThat(it.name).isEqualTo(validCreateUserRequest.name)
            assertThat(it.email).isEqualTo(validCreateUserRequest.email)
        }
    }
}