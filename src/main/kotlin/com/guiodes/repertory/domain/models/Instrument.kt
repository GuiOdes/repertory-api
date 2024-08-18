package com.guiodes.repertory.domain.models

import com.guiodes.repertory.domain.interfaces.BaseEntity
import java.time.LocalDateTime
import java.util.UUID

data class Instrument(
    override val id: UUID,
    val name: String,
    override val createdAt: LocalDateTime,
    override var updatedAt: LocalDateTime
) : BaseEntity()
