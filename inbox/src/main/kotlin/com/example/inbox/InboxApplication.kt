package com.example.inbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InboxApplication

fun main(args: Array<String>) {
    runApplication<InboxApplication>(*args)
}
