package com.henriquebarucco.movielie.entrypoint.amqp.dto

import com.henriquebarucco.movielie.movie.create.dto.CreateMovieCommand
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class SaveMovieDto(
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    val poster: String,
    val overview: String,
    @SerialName("release_date")
    val releaseDate: String,
    val status: String,
    @SerialName("external_reference")
    val externalReference: ExternalReferenceDto,
) {
    fun toCommand() =
        CreateMovieCommand(
            title = title,
            originalTitle = originalTitle,
            poster = poster,
            overview = overview,
            releaseDate = LocalDate.parse(releaseDate),
            status = status,
            externalProvider = externalReference.provider,
            externalId = externalReference.id,
        )
}

@Serializable
data class ExternalReferenceDto(
    val provider: String,
    val id: String,
)
