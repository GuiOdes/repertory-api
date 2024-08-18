package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.UserInstrumentGateway
import com.guiodes.repertory.domain.models.UserInstrument
import java.util.UUID

class RemoveUserInstrumentUseCase(
    private val userInstrumentGateway: UserInstrumentGateway,
) {
    fun execute(
        userId: UUID,
        instrumentId: UUID,
    ) {
        userInstrumentGateway.delete(UserInstrument(userId, instrumentId))
    }
}
