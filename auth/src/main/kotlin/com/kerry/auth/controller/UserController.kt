package com.kerry.auth.controller

import com.kerry.auth.controller.req.CreateUser
import com.kerry.auth.domain.User
import com.kerry.auth.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody request: CreateUser): User {
        return userService.createUser(
            username = request.username!!,
            encodedPassword = request.password!!
        )
    }
}