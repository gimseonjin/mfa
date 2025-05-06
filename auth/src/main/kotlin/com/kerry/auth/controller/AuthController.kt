package com.kerry.auth.controller

import com.kerry.auth.controller.req.CheckOtp
import com.kerry.auth.controller.req.CreateUser
import com.kerry.auth.controller.req.LoginUser
import com.kerry.auth.service.OtpService
import com.kerry.auth.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userService: UserService,
    private val otpService: OtpService
) {

    @PostMapping
    fun auth(
        @RequestBody loginUser: LoginUser
    ): String {
        val user = userService.authenticate(
            username = loginUser.username!!,
            password = loginUser.password!!
        )
        return otpService.renewOtp(user)
    }

    @PostMapping("/otp/check")
    fun check(
        @RequestBody checkOtp: CheckOtp
    ): Boolean {
        return otpService.checkOtp(
            username = checkOtp.username!!,
            otp = checkOtp.otp!!
        )
    }

}