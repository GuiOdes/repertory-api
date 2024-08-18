package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.UserInstrumentGateway
import java.util.UUID

class FindUserInstrumentsUseCase(
    private val userInstrumentGateway: UserInstrumentGateway,
) {
    fun execute(userId: UUID) = userInstrumentGateway.findAllByUser(userId)
}
