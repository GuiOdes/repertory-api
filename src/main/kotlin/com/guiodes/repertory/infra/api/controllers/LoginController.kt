package com.guiodes.repertory.infra.api.controllers

import com.guiodes.repertory.application.usecases.AuthenticateUserUseCase
import com.guiodes.repertory.application.usecases.DoRefreshTokenUseCase
import com.guiodes.repertory.infra.api.requests.LoginRequest
import com.guiodes.repertory.infra.api.responses.LoginResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val doRefreshTokenUseCase: DoRefreshTokenUseCase,
) {
    @PostMapping
    fun login(
        @RequestBody request: LoginRequest,
    ): LoginResponse = authenticateUserUseCase.execute(request)

    @PostMapping("/refresh")
    fun refresh(
        @RequestHeader("Authorization") token: String,
    ): LoginResponse = doRefreshTokenUseCase.execute(token)
}
