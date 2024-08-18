package com.guiodes.repertory.domain.api.requests

import com.guiodes.repertory.domain.models.Instrument
import java.time.LocalDateTime
import java.util.UUID

data class CreateInstrumentRequest(
    val name: String,
) {
    fun toDomain() =
        Instrument(
            id = UUID.randomUUID(),
            name = name,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
}
