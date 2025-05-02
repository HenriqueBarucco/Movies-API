package com.henriquebarucco.movielie.entrypoint.amqp

import com.henriquebarucco.movielie.entrypoint.amqp.messages.SaveMovieDto
import com.henriquebarucco.movielie.movie.save.SaveMovieUseCase
import com.henriquebarucco.movielie.shared.utils.Logger.Companion.getLogger
import kotlinx.serialization.json.Json
import org.slf4j.MDC
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class SaveMovieListener(
    private val json: Json,
    private val saveMovieUseCase: SaveMovieUseCase,
) {
    companion object {
        private const val MESSAGE_BODY = "MESSAGE_BODY"
    }

    private val logger = getLogger()

    @RabbitListener(
        queues = ["\${rabbitmq.queues.movies.save-movie.queue}"],
        errorHandler = "DeadLetterErrorHandler",
        concurrency = "\${rabbitmq.queues.movies.save-movie.concurrency}",
    )
    fun saveMovieMessage(message: Message) {
        val messageBody = json.decodeFromString<SaveMovieDto>(String(message.body))
        MDC.put(MESSAGE_BODY, messageBody.toString())

        try {
            this.logger.info("[SAVE_MOVIE] Received new message to save movie")

            val output = this.saveMovieUseCase.execute(messageBody.toCommand())

            this.logger.info("[SAVE_MOVIE] Movie saved successfully with id ${output.id} (${output.message})")
        } catch (ex: Exception) {
            this.logger.error("[SAVE_MOVIE] Failed to save movie", ex)
            throw ex
        } finally {
            MDC.clear()
        }
    }
}
