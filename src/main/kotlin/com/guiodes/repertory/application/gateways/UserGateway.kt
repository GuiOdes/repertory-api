package com.guiodes.repertory.application.gateways

import com.guiodes.repertory.domain.models.User
import java.util.UUID

interface UserGateway : BaseGateway<User> {
    fun findByEmail(email: String): User?

    fun existsById(id: UUID): Boolean

    fun restoreById(id: UUID)

    fun deleteById(id: UUID)
}
