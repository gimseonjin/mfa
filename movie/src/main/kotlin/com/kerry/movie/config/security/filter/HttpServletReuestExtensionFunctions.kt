package com.kerry.movie.config.security.filter

import jakarta.servlet.http.HttpServletRequest

fun HttpServletRequest.isLoginRequest(): Boolean {
    return this.requestURI == "/login"
}

fun HttpServletRequest.requiresAuthorization(): Boolean =
    !isLoginRequest()


// OTP 인증 시도인지
fun HttpServletRequest.isOtpAuthRequest(): Boolean =
    isLoginRequest() &&
            !this.getHeader("otp").isNullOrBlank()