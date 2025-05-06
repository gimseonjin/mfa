package com.kerry.movie.config.security.otp

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class OtpAuthentication(
    principal: Any,
    credentials: Any,
    authorities: Collection<GrantedAuthority>? = null
): UsernamePasswordAuthenticationToken(
    principal,
    credentials,
    authorities
) {
}