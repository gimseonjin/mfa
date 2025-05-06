package com.kerry.auth.service

import com.kerry.auth.domain.User
import com.kerry.auth.repository.UserJpaRepository
import com.kerry.auth.util.PasswordEncryptor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.security.sasl.AuthenticationException

@Service
class UserService(
    private val passwordEncryptor: PasswordEncryptor,
    private val userJpaRepository: UserJpaRepository
) {

    @Transactional
    fun createUser(username: String, encodedPassword: String): User {
        val isExistUser = userJpaRepository.findByUsername(username)
        require(isExistUser == null) { "User already exists" }

        return userJpaRepository.save(User.of(
            username = username,
            password = encodedPassword
        ))
    }

    @Transactional(readOnly = true)
    fun authenticate(username: String, password: String): User {
        val user = userJpaRepository.findByUsername(username)
            ?: throw AuthenticationException("Invalid username or password")

        if (!passwordEncryptor.matches(
                rawPassword =  password,
                encodedPassword =  user.password
        )) {
            throw AuthenticationException("Invalid username or password")
        }

        return user
    }
}