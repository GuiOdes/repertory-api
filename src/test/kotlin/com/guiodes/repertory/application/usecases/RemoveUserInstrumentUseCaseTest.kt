package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.UserInstrumentGateway
import com.guiodes.repertory.configs.UnitTest
import com.guiodes.repertory.domain.models.UserInstrument
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.util.UUID

class RemoveUserInstrumentUseCaseTest(
    @MockK private val gateway: UserInstrumentGateway
): UnitTest() {

    @InjectMockKs
    private lateinit var removeUserInstrumentUseCase: RemoveUserInstrumentUseCase

    @Test
    fun `should remove user instrument`() {
        val userId = UUID.randomUUID()
        val instrumentId = UUID.randomUUID()
        val userInstrument = UserInstrument(userId, instrumentId)

        every { gateway.delete(userInstrument) } just Runs

        removeUserInstrumentUseCase.execute(userId, instrumentId)

        verify(exactly = 1) { gateway.delete(userInstrument) }
    }
}