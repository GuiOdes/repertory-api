package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.gateways.AuthorityGateway
import com.guiodes.repertory.domain.api.requests.CreateAuthorityRequest

class CreateAuthorityUseCase(
    private val gateway: AuthorityGateway,
) {
    fun execute(createAuthorityRequest: CreateAuthorityRequest) = gateway.save(createAuthorityRequest.toDomain())
}
