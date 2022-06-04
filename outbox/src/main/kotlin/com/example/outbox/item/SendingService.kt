package com.example.outbox.item

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
    val kafkaTemplate: KafkaTemplate<String, String>,
    val transactionTemplate: TransactionTemplate,
    val mapper: ObjectMapper = jacksonObjectMapper()
) {
    @Scheduled(initialDelay = 6000L, fixedDelay = 6000L)
    fun sendItems() = itemRepository.getAllToBeSent(Pageable.ofSize(3)).content.forEach(this::sendItem)

    fun sendItem(item: Item) {
        val result = kafkaTemplate.send(item.type.name, mapper.writeValueAsString(item))

        result.addCallback(
            object : ListenableFutureCallback<SendResult<String, String>> {
                override fun onSuccess(result: SendResult<String, String>?) {
                    transactionTemplate.execute {
                        result?.producerRecord?.value()?.let { itemRepository.markAsSent(mapper.readValue<Item>(it).id) }
                    }
                }

                override fun onFailure(ex: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
    }
}