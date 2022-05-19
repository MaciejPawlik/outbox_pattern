package com.example.outbox.item

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Item(val id: UUID, val type: ItemType, val properties: String)

enum class ItemType {
    USER,
    PRODUCT
}

data class ItemDto(val type: ItemType, val properties: String)

data class Product(val name: String, val price: BigDecimal)

data class User(val name: String, val email: String)
