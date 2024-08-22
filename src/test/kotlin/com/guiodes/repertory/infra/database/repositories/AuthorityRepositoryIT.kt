package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.builders.AuthorityBuilder
import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.domain.models.Authority
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class AuthorityRepositoryIT(
    @Autowired private val authorityRepository: AuthorityRepository,
    @Autowired private val userRepository: UserRepository
) : BaseGatewayIT<Authority>(
        authorityRepository,
        AuthorityBuilder(),
    ) {
    @Test
    fun `Should update authority`() {
        val authority = authorityRepository.save(AuthorityBuilder().build())

        val authorityUpdated = authorityRepository.update(authority.copy(name = "New Name"))

        assertThat(authorityUpdated).isEqualTo(authority.copy(name = "New Name"))
    }

    @Test
    fun `Should verify if user is admin`() {
        val authority = authorityRepository.save(AuthorityBuilder().build())
        val user = userRepository.save(UserBuilder().build())
        val adminAuthority = authorityRepository.save(AuthorityBuilder().build().copy(name = "ADMIN"))

        authorityRepository.setToUser(user.id, authority.id)

        assertThat(authorityRepository.isUserAdmin(user.id)).isFalse()

        authorityRepository.setToUser(user.id, adminAuthority.id)

        assertThat(authorityRepository.isUserAdmin(user.id)).isTrue()
    }
}
