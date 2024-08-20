package com.guiodes.repertory.application.gateways

import com.guiodes.repertory.domain.models.Authority
import java.util.UUID

interface AuthorityGateway : BaseGateway<Authority> {
    fun findByUserId(userId: UUID): List<Authority>

    fun setToUser(
        userId: UUID,
        authorityId: UUID,
    )

    fun deleteByName(name: String)
}
