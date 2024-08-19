package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.InstrumentGateway
import com.guiodes.repertory.builders.InstrumentBuilder
import com.guiodes.repertory.configs.UnitTest
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Test

class FindInstrumentUseCaseTest(
    @MockK private val gateway: InstrumentGateway,
) : UnitTest() {
    @InjectMockKs
    private lateinit var findInstrumentUseCase: FindInstrumentUseCase

    @Test
    fun `should find instrument`() {
        val instrument = InstrumentBuilder().build()

        every { gateway.findByNameContaining(instrument.name) } returns listOf(instrument)

        findInstrumentUseCase.execute(instrument.name)

        verify(exactly = 1) { gateway.findByNameContaining(instrument.name) }
    }
}
