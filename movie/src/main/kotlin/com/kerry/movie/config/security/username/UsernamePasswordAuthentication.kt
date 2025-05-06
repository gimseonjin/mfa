package com.kerry.movie.config.security.username

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class UsernamePasswordAuthentication(
    principal: Any?,
    credentials: Any?,
    authorities: Collection<GrantedAuthority>? = null
): UsernamePasswordAuthenticationToken(
    principal,
    credentials,
    authorities
) {
}