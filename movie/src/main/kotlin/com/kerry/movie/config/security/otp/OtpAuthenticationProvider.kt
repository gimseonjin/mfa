package com.kerry.movie.config.security.otp

import com.kerry.movie.config.security.AuthenticationDelegator
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class OtpAuthenticationProvider(
    private val authenticationDelegator: AuthenticationDelegator
): AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val otp = authentication.credentials.toString()

        val isValid = authenticationDelegator.checkOtp(
            userName = username,
            otp = otp
        )

        if (!isValid) {
            throw BadCredentialsException("Invalid OTP")
        }

        return OtpAuthentication(username, otp)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return OtpAuthentication::class.java.isAssignableFrom(authentication)
    }
}