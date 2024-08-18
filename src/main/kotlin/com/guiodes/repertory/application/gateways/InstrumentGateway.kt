package com.guiodes.repertory.application.gateways

import com.guiodes.repertory.domain.models.Instrument
import java.util.UUID

interface InstrumentGateway : BaseGateway<Instrument> {
    fun existsById(id: UUID): Boolean
    fun findByNameContaining(name: String): List<Instrument>
}
