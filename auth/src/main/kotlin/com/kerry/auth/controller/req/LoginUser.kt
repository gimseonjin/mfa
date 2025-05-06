package com.kerry.auth.controller.req

data class LoginUser(
    var username: String? = null,
    var password: String? = null,
)