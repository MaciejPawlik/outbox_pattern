package com.example.outbox.item

import org.springframework.data.domain.Pageable
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.util.concurrent.ListenableFutureCallback

@Service
class SendingService(
    val itemRepository: ItemRepository,
    val kafkaTemplate: KafkaTemplate<String, Item>,
    val transactionTemplate: TransactionTemplate
) {
    @Scheduled(initialDelay = 6000L, fixedDelay = 6000L)
    fun sendItems() = itemRepository.getAllToBeSent(Pageable.ofSize(3)).content.forEach(this::sendItem)

    fun sendItem(item: Item) {
        val result = kafkaTemplate.send(item.type.name, item)

        result.addCallback(
            object : ListenableFutureCallback<SendResult<String, Item>> {
                override fun onSuccess(result: SendResult<String, Item>?) {
                    transactionTemplate.execute {
                        result?.producerRecord?.value()?.id?.let { itemRepository.markAsSent(it) }
                    }
                }

                override fun onFailure(ex: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
    }
}