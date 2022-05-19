package com.example.outbox.item

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class ItemEndpoint(val itemService: ItemService, val objectMapper: ObjectMapper) {

    @PostMapping("/products")
    fun addProduct(@RequestBody product: Product): ItemJpaEntity = itemService.addItem(ItemDto(ItemType.PRODUCT, objectMapper.writeValueAsString(product)))

    @PostMapping("/users")
    fun addUser(@RequestBody user: User): ItemJpaEntity = itemService.addItem(ItemDto(ItemType.USER, objectMapper.writeValueAsString(user)))
}