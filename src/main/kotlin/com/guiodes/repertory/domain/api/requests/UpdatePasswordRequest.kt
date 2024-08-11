package com.guiodes.repertory.domain.api.requests

import java.util.UUID

data class UpdatePasswordRequest(
    val userId: UUID,
    val oldPassword: String,
    val newPassword: String,
)
