package com.kerry.movie.config.security.filter

import com.kerry.movie.config.security.otp.OtpAuthentication
import com.kerry.movie.config.security.otp.OtpAuthenticationProvider
import com.kerry.movie.config.security.username.UsernamePasswordAuthentication
import com.kerry.movie.config.security.username.UsernamePasswordAuthenticationProvider
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class InitAuthFilter(
    private val otpAuthenticationProvider: OtpAuthenticationProvider,
    private val usernamePasswordAuthenticationProvider: UsernamePasswordAuthenticationProvider,
    @Value("\${jwt.signing-key}")
    private val signingKey: String,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        when (request.isOtpAuthRequest()) {
            true  -> authenticateWithOtp(request, response)
            false -> authenticateWithUsernamePassword(request)
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.isLoginRequest()
    }

    private fun authenticateWithUsernamePassword(request: HttpServletRequest) {
        val username = request.getHeader("username").orEmpty()
        val password = request.getHeader("password").orEmpty()

        UsernamePasswordAuthentication(username, password)
            .also(usernamePasswordAuthenticationProvider::authenticate)
    }

    private fun authenticateWithOtp(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val username = request.getHeader("username").orEmpty()
        val otp      = request.getHeader("otp").orEmpty()

        OtpAuthentication(username, otp)
            .also(otpAuthenticationProvider::authenticate)

        issueJwt(response, username)
    }

    private fun issueJwt(response: HttpServletResponse, username: String) {
        val key = Keys.hmacShaKeyFor(signingKey.toByteArray())
        val jwt = Jwts.builder()
            .claim("username", username)
            .signWith(key)
            .compact()

        response.addHeader("Authorization", "Bearer $jwt")
    }
}


