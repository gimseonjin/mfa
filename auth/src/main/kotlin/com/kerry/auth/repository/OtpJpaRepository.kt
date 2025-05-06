package com.kerry.auth.repository

import com.kerry.auth.domain.Otp
import org.springframework.data.jpa.repository.JpaRepository

interface OtpJpaRepository: JpaRepository<Otp, Long> {
    fun findByUsername(username: String): Otp?
}