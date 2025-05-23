package com.kerry.auth.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    private val user: User
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
    }
}