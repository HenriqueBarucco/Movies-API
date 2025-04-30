package com.henriquebarucco.movielie.entrypoint.amqp.handler

import com.henriquebarucco.movielie.shared.utils.Logger.Companion.getLogger
import com.rabbitmq.client.Channel
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException
import org.springframework.stereotype.Component

@Component("DeadLetterErrorHandler")
class DeadLetterErrorHandler(
    private val rabbitTemplate: RabbitTemplate,
) : RabbitListenerErrorHandler {
    companion object {
        private const val MAX_RETRIES = 3
        private const val RETRY_DELAY = "10000" // 10 seconds
    }

    private val logger = getLogger()

    override fun handleError(
        message: Message,
        p1: Channel,
        p2: org.springframework.messaging.Message<*>?,
        ex: ListenerExecutionFailedException,
    ): Any? {
        val retryCount =
            message.messageProperties.headers
                .getOrDefault("x-death", emptyList<Any>())
                .let { xDeath ->
                    if (xDeath is List<*> && xDeath.isNotEmpty()) {
                        val xDeathInfo = xDeath[0] as Map<*, *>
                        (xDeathInfo["count"] as? Long)?.toInt() ?: 0
                    } else {
                        0
                    }
                }

        val queueName = getQueueName(message)
        val exchangeName = getExchangeName(message)

        if (retryCount >= MAX_RETRIES) {
            this.logger.error("Max retries reached, sending to parking lot", ex)

            message.messageProperties.headers["x-exception-message"] = ex.cause?.message ?: "Unknown error"
            message.messageProperties.headers["x-exception-stacktrace"] = ex.cause?.stackTraceToString()

            this.rabbitTemplate.send(
                exchangeName,
                "$queueName.parking-lot",
                message,
            )
        } else {
            this.logger.warn("Retrying message, attempt: ${retryCount + 1}")

            message.messageProperties.expiration = RETRY_DELAY

            this.rabbitTemplate.send(
                exchangeName,
                "$queueName.wait",
                message,
            )
        }

        return null
    }

    private fun getQueueName(message: Message): String = message.messageProperties.consumerQueue

    private fun getExchangeName(message: Message): String = message.messageProperties.receivedExchange
}
