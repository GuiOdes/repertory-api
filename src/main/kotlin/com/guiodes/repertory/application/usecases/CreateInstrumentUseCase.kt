package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.InstrumentGateway
import com.guiodes.repertory.domain.api.requests.CreateInstrumentRequest

class CreateInstrumentUseCase(
    private val instrumentGateway: InstrumentGateway,
) {
    fun execute(instrumentRequest: CreateInstrumentRequest) = instrumentGateway.save(instrumentRequest.toDomain())
}
