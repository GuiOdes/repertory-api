package com.guiodes.repertory.domain.models

import com.guiodes.repertory.domain.interfaces.BaseEntity
import java.time.LocalDate

data class Repertory(
    val playAt: LocalDate,
    val musicList: List<RepertoryMusic>,
) : BaseEntity() {
    data class RepertoryMusic(
        val music: Music,
        val observations: String,
    )
}
