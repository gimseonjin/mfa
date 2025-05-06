package com.kerry.auth.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var username: String,

    @Column(nullable = false)
    var password: String,
) {
    companion object {
        fun of(username: String, password: String): User {
            return User(
                username = username,
                password = password
            )
        }
    }
}