package com.example.outbox.item

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "items")
class ItemJpaEntity (
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    private var id: UUID?,

    @Enumerated(EnumType.STRING)
    private var type: ItemType,

    private var properties: String,

    private var created: LocalDateTime
)