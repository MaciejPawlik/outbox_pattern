package com.example.outbox.item

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ItemRepository : CrudRepository<ItemJpaEntity, UUID>