package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.InstrumentGateway

class FindInstrumentUseCase(
    private val instrumentGateway: InstrumentGateway,
) {
    fun execute(name: String) = instrumentGateway.findByNameContaining(name)
}
