package com.guiodes.repertory.infra.api.controllers

import com.guiodes.repertory.application.usecases.CreateUserUseCase
import com.guiodes.repertory.builders.AuthorityBuilder
import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.configs.IntegrationTest
import com.guiodes.repertory.domain.api.requests.LoginRequest
import com.guiodes.repertory.domain.api.responses.LoginResponse
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.infra.database.repositories.AuthorityRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.security.oauth2.jwt.JwtDecoder

class LoginControllerIT(
    @Autowired private val testRestTemplate: TestRestTemplate,
    @Autowired private val createUserUseCase: CreateUserUseCase,
    @Autowired private val jwtDecoder: JwtDecoder,
    @Autowired private val authorityRepository: AuthorityRepository,
) : IntegrationTest() {
    private val request = UserBuilder().buildRequest()
    private val authorities = listOf(AuthorityBuilder().build())

    private lateinit var user: User

    @BeforeEach
    fun setup() {
        user = createUserUseCase.execute(request)
        authorityRepository.save(authorities.first())
        authorityRepository.setToUser(user.id, authorities.first().id)
    }

    @Test
    fun `Should authenticate user with success`() {
        val loginRequest =
            LoginRequest(
                email = user.email,
                password = request.password,
            )

        val response = testRestTemplate.postForEntity("/login", loginRequest, LoginResponse::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        response.body.also {
            val jwt = jwtDecoder.decode(it!!.token)

            assertThat(jwt.subject).isEqualTo(user.id.toString())
            assertThat(jwt.claims["email"]).isEqualTo(user.email)
            assertThat(jwt.claims["name"]).isEqualTo(user.name)
            assertThat(jwt.claims["authorities"]).isEqualTo(authorities.map { it.name })
        }
    }

    @Test
    fun `Should return 401 when user not found`() {
        val loginRequest =
            LoginRequest(
                email = "",
                password = "",
            )

        val response = testRestTemplate.postForEntity("/login", loginRequest, LoginResponse::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
    }

    @Test
    fun `Should return 401 when password is invalid`() {
        val loginRequest =
            LoginRequest(
                email = user.email,
                password = "invalid",
            )

        val response = testRestTemplate.postForEntity("/login", loginRequest, LoginResponse::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
    }

    @Test
    fun `Should refresh token with success`() {
        val loginRequest = request

        val loginResponse = testRestTemplate.postForEntity("/login", loginRequest, LoginResponse::class.java).body!!

        val headers = HttpHeaders()

        headers.set("Authorization", "Bearer ${loginResponse.token}")

        val requestEntity = RequestEntity.post("/login/refresh").headers(headers).build()

        val response = testRestTemplate.exchange(requestEntity, LoginResponse::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.token).isNotBlank()
    }
}
