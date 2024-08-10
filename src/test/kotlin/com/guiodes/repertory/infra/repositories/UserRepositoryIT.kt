package com.guiodes.repertory.infra.repositories

import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.utils.isEqualToIgnoringDates
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UserRepositoryIT(
    @Autowired private val userRepository: UserRepository
): BaseRepositoryIT<User>(
    userRepository,
    UserBuilder()
) {

    private val entity = UserBuilder().build()

    @Test
    fun `should find user by email`() {
        val user = userRepository.save(entity)
        val userFound = userRepository.findByEmail(user.email)

        assertThat(userFound).isEqualToIgnoringDates(user)
    }

    @Test
    fun `should delete and restore user`() {
        val user = userRepository.save(entity)

        userRepository.delete(user)

        val userDeleted = userRepository.findByEmail(user.email)

        userRepository.restoreById(user.id!!)

        val userRestored = userRepository.findByEmail(user.email)

        assertThat(userDeleted).isNull()
        assertThat(userRestored).isEqualToIgnoringDates(user)
    }

    @Test
    fun `should update user`() {
        val user = userRepository.save(entity)

        val userUpdated = userRepository.update(user.copy(name = "New Name"))

        assertThat(userUpdated).isEqualTo(user.copy(name = "New Name"))
    }
}