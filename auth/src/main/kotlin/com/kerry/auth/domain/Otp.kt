package com.kerry.auth.domain

import jakarta.persistence.*

@Entity
@Table(name = "otp")
class Otp(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var otpCode: String,
) {
    companion object {
        fun of(username: String, otpCode: String): Otp {
            return Otp(
                username = username,
                otpCode = otpCode
            )
        }
    }

    fun renewOtp(otpCode: String) {
        this.otpCode = otpCode
    }

    fun isValid(otpCode: String): Boolean {
        return this.otpCode == otpCode
    }
}