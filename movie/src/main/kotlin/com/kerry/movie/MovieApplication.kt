package com.kerry.movie

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MovieApplication

fun main(args: Array<String>) {
    runApplication<MovieApplication>(*args)
}
