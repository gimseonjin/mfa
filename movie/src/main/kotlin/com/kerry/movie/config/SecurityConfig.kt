package com.kerry.movie.config

import com.kerry.movie.config.security.filter.InitAuthFilter
import com.kerry.movie.config.security.filter.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class SecurityConfig(
    private val initAuthFilter: InitAuthFilter,
    private val jwtAuthFilter: JwtAuthFilter
) {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.addFilterBefore(initAuthFilter, BasicAuthenticationFilter::class.java)
        httpSecurity.addFilterAfter(jwtAuthFilter, BasicAuthenticationFilter::class.java)

        httpSecurity.csrf { it.disable() }

        httpSecurity.authorizeHttpRequests { it.anyRequest().authenticated() }

        return httpSecurity.build()
    }
}