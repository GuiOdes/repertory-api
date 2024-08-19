package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.UserInstrumentGateway
import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.configs.UnitTest
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Test

class FindUserInstrumentsUseCaseTest(
    @MockK private val gateway: UserInstrumentGateway,
) : UnitTest() {
    @InjectMockKs
    private lateinit var findUserInstrumentsUseCase: FindUserInstrumentsUseCase

    @Test
    fun `should find user instruments`() {
        val user = UserBuilder().build()

        every { gateway.findAllByUser(user.id) } returns emptyList()

        findUserInstrumentsUseCase.execute(user.id)

        verify(exactly = 1) { gateway.findAllByUser(user.id) }
    }
}
