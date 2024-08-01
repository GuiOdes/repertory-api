package com.guiodes.repertory.infra.api.controllers

import com.guiodes.repertory.application.usecases.CreateUserUseCase
import com.guiodes.repertory.domain.models.User
import com.guiodes.repertory.infra.api.requests.CreateUserRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val createUserUseCase: CreateUserUseCase
) {

    @PostMapping("/new")
    fun createUser(@RequestBody request: CreateUserRequest): User {
        return createUserUseCase.execute(request)
    }
}