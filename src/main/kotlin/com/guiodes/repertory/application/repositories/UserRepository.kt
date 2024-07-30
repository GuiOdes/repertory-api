package com.guiodes.repertory.application.repositories

import com.guiodes.repertory.domain.models.User

interface UserRepository: BaseRepositoryT<User> {
    fun findByEmailAndPassword(email: String, password: String): User?
}
