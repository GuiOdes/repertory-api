package com.guiodes.repertory.domain.models

import com.guiodes.repertory.domain.interfaces.BaseEntity
import org.springframework.security.core.GrantedAuthority
import java.time.LocalDateTime
import java.util.UUID

data class Authority(
    override val id: UUID,
    val name: String,
    override val createdAt: LocalDateTime,
    override var updatedAt: LocalDateTime,
) : BaseEntity(),
    GrantedAuthority {
    override fun getAuthority() = name
}
