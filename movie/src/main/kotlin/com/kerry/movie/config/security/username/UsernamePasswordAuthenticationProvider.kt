package com.kerry.movie.config.security.username

import com.kerry.movie.config.security.AuthenticationDelegator
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class UsernamePasswordAuthenticationProvider(
    private val authenticationDelegator: AuthenticationDelegator
): AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()

        authenticationDelegator.requestOtp(
            userName = username,
            password = password,
        )

        return UsernamePasswordAuthentication(username, password)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthentication::class.java.isAssignableFrom(authentication)
    }
}