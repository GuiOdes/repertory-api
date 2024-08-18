package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.InstrumentGateway
import com.guiodes.repertory.builders.InstrumentBuilder
import com.guiodes.repertory.configs.UnitTest
import com.guiodes.repertory.domain.api.requests.CreateInstrumentRequest
import com.guiodes.repertory.domain.models.Instrument
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CreateInstrumentUseCaseTest(
    @MockK private val gateway: InstrumentGateway
): UnitTest() {

    @InjectMockKs
    private lateinit var createInstrumentUseCase: CreateInstrumentUseCase

    @Test
    fun `should create instrument`() {
        val request = CreateInstrumentRequest(
            name = "Guitar"
        )
        val instrument = slot<Instrument>()

        every { gateway.save(capture(instrument)) } returns InstrumentBuilder().build().copy(name = request.name)

        createInstrumentUseCase.execute(request)

        assertThat(instrument.captured.name).isEqualTo(request.name)

        verify(exactly = 1) { gateway.save(instrument.captured) }
    }
}