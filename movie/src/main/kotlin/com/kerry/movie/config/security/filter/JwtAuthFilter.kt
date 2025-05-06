package com.kerry.movie.config.security.filter

import com.kerry.movie.config.security.otp.OtpAuthentication
import com.kerry.movie.config.security.username.UsernamePasswordAuthentication
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter (
    @Value("\${jwt.signing-key}")
    private val signingKey: String,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 1) 토큰과 키 추출
        val jwtToken = request.getHeader("Authorization")
            ?.split(" ")
            ?.getOrNull(1)
        val secretKey = Keys.hmacShaKeyFor(signingKey.toByteArray())

        // 2) 클레임 파싱
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(jwtToken)
            .payload

        // 3) Authentication 생성
        val username = claims["username"] as String
        val authority = SimpleGrantedAuthority("user")
        val authentication = UsernamePasswordAuthentication(
            principal   = username,
            credentials = null,
            authorities = listOf(authority)
        )
        SecurityContextHolder.getContext().authentication = authentication

        // 4) 다음 필터로
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.requiresAuthorization()
    }
}