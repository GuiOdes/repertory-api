package com.guiodes.repertory.infra.api.controllers

import com.guiodes.repertory.application.usecases.CreateUserUseCase
import com.guiodes.repertory.domain.api.requests.CreateUserRequest
import com.guiodes.repertory.domain.models.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
) {
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @RequestBody request: CreateUserRequest,
    ): User = createUserUseCase.execute(request)
}
