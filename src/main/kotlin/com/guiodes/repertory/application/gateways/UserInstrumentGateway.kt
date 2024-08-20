package com.guiodes.repertory.application.gateways

import com.guiodes.repertory.domain.models.Instrument
import com.guiodes.repertory.domain.models.UserInstrument
import java.util.UUID

interface UserInstrumentGateway : BaseGateway<UserInstrument> {
    fun findAllByUser(userId: UUID): List<Instrument>

    fun delete(userInstrument: UserInstrument)
}
