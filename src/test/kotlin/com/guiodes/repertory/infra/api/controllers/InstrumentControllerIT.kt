package com.guiodes.repertory.infra.api.controllers

import com.guiodes.repertory.application.usecases.AuthenticateUserUseCase
import com.guiodes.repertory.application.usecases.CreateUserUseCase
import com.guiodes.repertory.builders.InstrumentBuilder
import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.configs.IntegrationTest
import com.guiodes.repertory.domain.api.requests.CreateInstrumentRequest
import com.guiodes.repertory.domain.api.requests.LoginRequest
import com.guiodes.repertory.domain.models.Instrument
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.infra.database.repositories.InstrumentRepository
import com.guiodes.repertory.utils.isEqualToIgnoringDates
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity

class InstrumentControllerIT(
    @Autowired private val testRestTemplate: TestRestTemplate,
    @Autowired private val instrumentRepository: InstrumentRepository,
    @Autowired private val authenticateUserUseCase: AuthenticateUserUseCase,
    @Autowired private val createUserUseCase: CreateUserUseCase,
) : IntegrationTest() {
    private val instrument = InstrumentBuilder().build()
    private val userRequest = UserBuilder().buildRequest()
    private lateinit var user: User
    private lateinit var token: String

    @BeforeEach
    fun setup() {
        user = createUserUseCase.execute(userRequest)

        token =
            authenticateUserUseCase
                .execute(
                    LoginRequest(
                        email = user.email,
                        password = userRequest.password,
                    ),
                ).token
    }

    @Test
    fun `Should create an instrument with success`() {
        assertThat(instrumentRepository.findByNameContaining(instrument.name).size).isEqualTo(0)

        val requestEntity =
            RequestEntity
                .post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .body(CreateInstrumentRequest(instrument.name))

        val response =
            testRestTemplate.exchange(
                requestEntity,
                Instrument::class.java,
            )

        assertThat(response.statusCode).isEqualToIgnoringDates(HttpStatus.OK)
        assertThat(instrumentRepository.findByNameContaining(instrument.name).size).isEqualTo(1)
    }

    @Test
    fun `Should list all instruments and filter with success`() {
        val instrument2 = InstrumentBuilder().build().copy(name = "Guitar2")
        val instrument3 = InstrumentBuilder().build().copy(name = "Guitar3")

        val instruments =
            listOf(
                instrument,
                instrument2,
                instrument3,
            )

        instruments.forEach {
            instrumentRepository.save(it)
        }

        val requestEntity =
            RequestEntity
                .get("$BASE_URL/list/name/Guitar")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .build()

        val response =
            testRestTemplate.exchange(
                requestEntity,
                Array<Instrument>::class.java,
            )

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.size).isEqualTo(1)
        assertThat(response.body!!.first()).isEqualToIgnoringDates(instrument)

        val requestEntity2 =
            RequestEntity
                .get("$BASE_URL/list/name/Guitar2")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .build()

        val response2 =
            testRestTemplate.exchange(
                requestEntity2,
                Array<Instrument>::class.java,
            )

        assertThat(response2.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response2.body!!.size).isEqualTo(1)
        assertThat(response2.body!!.first()).isEqualToIgnoringDates(instrument2)
    }

    @Test
    fun `Should list an instrument by id with success`() {
        val instrument2 = InstrumentBuilder().build().copy(name = "Guitar2")
        val instrument3 = InstrumentBuilder().build().copy(name = "Guitar3")

        val instruments =
            listOf(
                instrument,
                instrument2,
                instrument3,
            )

        instruments.forEach {
            instrumentRepository.save(it)
        }

        val requestEntity =
            RequestEntity
                .get("$BASE_URL/list/id/${instrument.id}")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .build()

        val response =
            testRestTemplate.exchange(
                requestEntity,
                Instrument::class.java,
            )

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualToIgnoringDates(instrument)
    }

    companion object {
        private const val BASE_URL = "/instrument"
    }
}
