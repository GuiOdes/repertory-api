package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.InstrumentGateway
import java.util.UUID

class FindInstrumentUseCase(
    private val instrumentGateway: InstrumentGateway,
) {
    fun execute(name: String) = instrumentGateway.findByNameContaining(name)

    fun execute(id: UUID) = instrumentGateway.findById(id)
}
