package com.kerry.auth.controller.req

import com.kerry.auth.annotation.PasswordEncryption

data class CreateUser(
    var username: String? = null,
    @PasswordEncryption
    var password: String? = null,
)