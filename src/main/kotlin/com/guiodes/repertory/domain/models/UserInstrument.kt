package com.guiodes.repertory.domain.models

import java.util.UUID

data class UserInstrument(
    val userId: UUID,
    val instrumentId: UUID,
)
