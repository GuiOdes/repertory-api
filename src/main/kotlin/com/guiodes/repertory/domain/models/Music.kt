package com.guiodes.repertory.domain.models

import com.guiodes.repertory.domain.interfaces.BaseEntity
import java.util.UUID

data class Music(
    val name: String,
    val key: String,
    val chordLink: String,
    val lyricsLink: String,
    val videoLink: String,
    val capo: String,
    val isReadyToPlay: Boolean,
    val ministryId: UUID,
) : BaseEntity()
