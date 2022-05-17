package com.example.outbox.item

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ItemService(val itemRepository: ItemRepository) {

    fun addItem(item: ItemDto) = itemRepository.save(ItemJpaEntity(null, item.type, item.properties, LocalDateTime.now()))
}