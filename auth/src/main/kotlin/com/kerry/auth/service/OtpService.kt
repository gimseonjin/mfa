package com.kerry.auth.service

import com.kerry.auth.domain.Otp
import com.kerry.auth.domain.User
import com.kerry.auth.repository.OtpJpaRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class OtpService(
    private val otpJpaRepository: OtpJpaRepository
) {
    fun checkOtp(
        username:String,
        otp: String
    ): Boolean {
        return otpJpaRepository.findByUsername(username)?.isValid(otp)
            ?: false
    }

    fun renewOtp(
        user:User,
    ): String {
        val otp: String = generateOtpCode()

        val targetOtp = otpJpaRepository.findByUsername(user.username)
            ?.apply { renewOtp(otpCode) }
            ?: Otp.of(
                username = user.username,
                otpCode = otp
            )

        return otpJpaRepository.save(targetOtp).otpCode
    }

    private fun generateOtpCode(): String =
        Random.nextInt(100_000, 1_000_000).toString()
}