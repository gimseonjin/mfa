package com.kerry.auth.repository

import com.kerry.auth.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}