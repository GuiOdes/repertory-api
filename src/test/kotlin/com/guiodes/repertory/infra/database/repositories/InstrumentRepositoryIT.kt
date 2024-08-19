package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.builders.InstrumentBuilder
import com.guiodes.repertory.domain.models.Instrument
import com.guiodes.repertory.utils.isEqualToIgnoringDates
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class InstrumentRepositoryIT(
    @Autowired private val instrumentRepository: InstrumentRepository,
) : BaseGatewayIT<Instrument>(
        instrumentRepository,
        InstrumentBuilder(),
    ) {
    private val instrument = InstrumentBuilder().build()

    @Test
    fun `Should test findByNameContaining`() {
        instrumentRepository.save(instrument)

        val foundEntity = instrumentRepository.findByNameContaining(instrument.name)

        assertThat(foundEntity.firstOrNull()).isEqualToIgnoringDates(instrument)
    }

    @Test
    fun `Should test existsById`() {
        assertThat(instrumentRepository.existsById(instrument.id)).isFalse()

        instrumentRepository.save(instrument)

        assertThat(instrumentRepository.existsById(instrument.id)).isTrue()
    }

    @Test
    fun `Should update instrument`() {
        val instrumentSaved = instrumentRepository.save(instrument)

        instrumentRepository.update(instrumentSaved.copy(name = "New Name"))

        assertThat(instrumentRepository.findById(instrumentSaved.id)).isEqualTo(instrumentSaved.copy(name = "New Name"))
    }
}
