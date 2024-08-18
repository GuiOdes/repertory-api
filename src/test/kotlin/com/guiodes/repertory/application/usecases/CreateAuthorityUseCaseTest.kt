package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.AuthorityGateway
import com.guiodes.repertory.builders.AuthorityBuilder
import com.guiodes.repertory.configs.UnitTest
import com.guiodes.repertory.domain.api.requests.CreateAuthorityRequest
import com.guiodes.repertory.domain.models.Authority
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CreateAuthorityUseCaseTest(
    @MockK private val gateway: AuthorityGateway
): UnitTest() {

    @InjectMockKs
    private lateinit var createAuthorityUseCase: CreateAuthorityUseCase

    @Test
    fun `should create authority`() {
        val request = CreateAuthorityRequest(
            name = "authority"
        )
        val authority = slot<Authority>()

        every { gateway.save(capture(authority)) } returns AuthorityBuilder().build()

        createAuthorityUseCase.execute(request)

        verify(exactly = 1) { gateway.save(authority.captured) }

        assertThat(authority.captured.name).isEqualTo(request.name)
    }
}