package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.builders.AuthorityBuilder
import com.guiodes.repertory.domain.models.Authority
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class AuthorityRepositoryIT(
    @Autowired private val authorityRepository: AuthorityRepository,
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
}
