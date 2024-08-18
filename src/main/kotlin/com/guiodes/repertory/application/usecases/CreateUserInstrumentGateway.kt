package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.exceptions.NotFoundException
import com.guiodes.repertory.application.gateways.InstrumentGateway
import com.guiodes.repertory.application.gateways.UserGateway
import com.guiodes.repertory.application.gateways.UserInstrumentGateway
import com.guiodes.repertory.domain.models.Instrument
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.domain.models.UserInstrument
import java.util.UUID

class CreateUserInstrumentGateway(
    private val userInstrumentGateway: UserInstrumentGateway,
    private val userGateway: UserGateway,
    private val instrumentGateway: InstrumentGateway,
) {
    fun execute(
        userId: UUID,
        instrumentId: UUID,
    ) {
        if (!userGateway.existsById(userId)) {
            throw NotFoundException(User::class)
        }

        if (!instrumentGateway.existsById(instrumentId)) {
            throw NotFoundException(Instrument::class)
        }

        userInstrumentGateway.save(
            UserInstrument(userId = userId, instrumentId = instrumentId),
        )
    }
}
