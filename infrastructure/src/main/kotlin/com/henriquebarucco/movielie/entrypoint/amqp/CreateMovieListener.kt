package com.henriquebarucco.movielie.entrypoint.amqp

import com.henriquebarucco.movielie.entrypoint.amqp.dto.SaveMovieDto
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

    @RabbitListener(queues = ["\${rabbitmq.queues.movies.create-movie.name}"])
    fun createMovieMessage(message: Message) {
        val messageBody = json.decodeFromString<SaveMovieDto>(String(message.body))
        MDC.put(MESSAGE_BODY, messageBody.toString())

        try {
            this.logger.info("[CREATE_MOVIE] Received new message to create movie")
            this.createMovieUseCase.execute(messageBody.toCommand())
        } finally {
            this.logger.info("[CREATE_MOVIE] Movie created successfully")
            MDC.clear()
        }
    }
}
