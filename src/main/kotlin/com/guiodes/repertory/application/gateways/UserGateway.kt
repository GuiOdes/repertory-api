package com.guiodes.repertory.application.gateways

import com.guiodes.repertory.domain.models.User
import java.util.UUID

interface UserGateway : BaseGateway<User> {
    fun findByEmail(email: String): User?

    fun restoreById(id: UUID)
}
