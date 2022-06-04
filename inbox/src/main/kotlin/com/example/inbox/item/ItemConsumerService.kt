package com.example.inbox.item

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ItemConsumerService {

    @KafkaListener(topics = ["PRODUCT", "USER"], groupId = "outbox")
    fun getItems(message: String) {
        println(message)
    }
}