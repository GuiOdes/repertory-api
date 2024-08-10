package com.guiodes.repertory.application.repositories

import com.guiodes.repertory.domain.models.User
import java.util.UUID

interface UserRepository : BaseRepository<User> {
    fun findByEmail(email: String): User?
    fun restoreById(id: UUID)
}
