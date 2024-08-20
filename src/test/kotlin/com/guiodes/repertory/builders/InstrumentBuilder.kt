package com.guiodes.repertory.builders

import com.guiodes.repertory.domain.models.Instrument
import java.time.LocalDateTime
import java.util.UUID

class InstrumentBuilder : BaseEntityBuilder<Instrument> {
    private val id: UUID = UUID.randomUUID()
    private val name: String = "Guitar"
    private val createdAt: LocalDateTime = LocalDateTime.now()
    private val updatedAt: LocalDateTime = LocalDateTime.now()

    override fun build() =
        Instrument(
            id = id,
            name = name,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
}
