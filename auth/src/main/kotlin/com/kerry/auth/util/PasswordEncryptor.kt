package com.kerry.auth.util

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncryptor(
    private val passwordEncoder: PasswordEncoder
) {
    fun encryptPassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}