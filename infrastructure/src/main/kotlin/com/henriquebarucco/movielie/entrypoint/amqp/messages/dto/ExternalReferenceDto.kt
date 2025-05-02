package com.henriquebarucco.movielie.entrypoint.amqp.messages.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExternalReferenceDto(
    val id: String,
    val provider: String,
)
