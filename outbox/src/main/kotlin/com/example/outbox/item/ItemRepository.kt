package com.example.outbox.item

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ItemRepository : CrudRepository<ItemJpaEntity, UUID> {
    @Query("select new com.example.outbox.item.Item(i.id, i.type, i.properties) " +
            "from com.example.outbox.item.ItemJpaEntity i where i.sent is null")
    fun getAllToBeSent(pageable: Pageable): Page<Item>

    @Modifying
    @Query("update ItemJpaEntity i set i.sent = current_timestamp where i.id = :id")
    fun markAsSent(id: UUID): Int
}