package com.henriquebarucco.movielie.entrypoint.amqp.messages.dto

import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    val id: String,
    val name: String,
    val url: String,
    val type: String,
)
