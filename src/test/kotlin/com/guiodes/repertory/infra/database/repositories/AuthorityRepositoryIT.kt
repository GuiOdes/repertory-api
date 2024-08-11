package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.builders.AuthorityBuilder
import com.guiodes.repertory.domain.models.Authority
import org.springframework.beans.factory.annotation.Autowired

class AuthorityRepositoryIT(
    @Autowired private val authorityRepository: AuthorityRepository,
) : BaseGatewayIT<Authority>(
        authorityRepository,
        AuthorityBuilder(),
    )
