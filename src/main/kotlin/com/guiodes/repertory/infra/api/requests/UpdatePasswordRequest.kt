package com.guiodes.repertory.infra.api.requests

import java.util.UUID

data class UpdatePasswordRequest(
    val userId: UUID,
    val oldPassword: String,
    val newPassword: String,
)
