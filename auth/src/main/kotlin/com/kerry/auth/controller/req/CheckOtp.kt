package com.kerry.auth.controller.req

data class CheckOtp(
    var username: String? = null,
    var otp: String? = null
)