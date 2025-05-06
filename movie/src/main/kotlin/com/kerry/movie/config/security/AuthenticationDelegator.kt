package com.kerry.movie.config.security

import com.kerry.movie.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class AuthenticationDelegator(
    @Value("\${base-url.auth}")
    private val baseUrl: String,
    private val restTemplate: RestTemplate,
) {

    fun requestOtp(
        userName: String,
        password: String,
    ){
        val url = "$baseUrl/api/v1/auth"

        val user = User(
            username = userName,
            password = password,
        )

        restTemplate.postForEntity(url, HttpEntity(user), Void::class.java)
    }

    fun checkOtp(
        userName: String,
        otp: String,
    ): Boolean {
        val url = "$baseUrl/api/v1/auth/otp/check"

        val user = User(
            username = userName,
            otp = otp,
        )

        return restTemplate.postForEntity(url, HttpEntity(user), Boolean::class.java).body ?: false
    }

}