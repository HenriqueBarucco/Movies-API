package com.henriquebarucco.movielie.entrypoint.amqp

import com.henriquebarucco.movielie.entrypoint.amqp.messages.CreateMovieDto
import com.henriquebarucco.movielie.movie.create.CreateMovieUseCase
import com.henriquebarucco.movielie.shared.utils.Logger.Companion.getLogger
import kotlinx.serialization.json.Json
import org.slf4j.MDC
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class CreateMovieListener(
    private val json: Json,
    private val createMovieUseCase: CreateMovieUseCase,
) {
    companion object {
        private const val MESSAGE_BODY = "MESSAGE_BODY"
    }

    private val logger = getLogger()

    @RabbitListener(
        queues = ["\${rabbitmq.queues.movies.create-movie.queue}"],
        errorHandler = "DeadLetterErrorHandler",
        concurrency = "\${rabbitmq.queues.movies.create-movie.concurrency}",
    )
    fun createMovieMessage(message: Message) {
        val messageBody = json.decodeFromString<CreateMovieDto>(String(message.body))
        MDC.put(MESSAGE_BODY, messageBody.toString())

        try {
            this.logger.info("[CREATE_MOVIE] Received new message to create movie")
            this.createMovieUseCase.execute(messageBody.toCommand())
            this.logger.info("[CREATE_MOVIE] Movie created successfully")
        } catch (ex: Exception) {
            this.logger.error("[CREATE_MOVIE] Failed to create movie", ex)
            throw ex
        } finally {
            MDC.clear()
        }
    }
}
