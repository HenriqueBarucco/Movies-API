package com.henriquebarucco.movielie.entrypoint.amqp.messages

import com.henriquebarucco.movielie.entrypoint.amqp.messages.dto.ExternalReferenceDto
import com.henriquebarucco.movielie.entrypoint.amqp.messages.dto.VideoDto
import com.henriquebarucco.movielie.movie.create.dto.CreateMovieCommand
import com.henriquebarucco.movielie.movie.enum.VideoType
import com.henriquebarucco.movielie.movie.vo.Url
import com.henriquebarucco.movielie.movie.vo.Video
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMovieDto(
    @SerialName("external_reference")
    val externalReference: ExternalReferenceDto,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("original_language")
    val originalLanguage: String,
    val poster: String,
    val backdrop: String?,
    val overview: String,
    @SerialName("imdb_id")
    val imdbId: String?,
    val status: String,
    val duration: Int,
    @SerialName("release_date")
    val releaseDate: String,
    val videos: List<VideoDto>,
    val genres: List<String>,
    val keywords: List<String>,
) {
    fun toCommand() =
        CreateMovieCommand(
            externalId = this.externalReference.id,
            externalProvider = this.externalReference.provider,
            title = this.title,
            originalTitle = this.originalTitle,
            originalLanguage = this.originalLanguage,
            poster = this.poster,
            backdrop = this.backdrop,
            overview = this.overview,
            imdbId = this.imdbId,
            status = this.status,
            duration = this.duration,
            releaseDate = this.releaseDate,
            videos =
                this.videos.map {
                    Video(
                        id = it.id,
                        name = it.name,
                        url = Url(it.url),
                        type = VideoType.valueOf(it.type),
                    )
                },
            genres = this.genres,
            keywords = this.keywords,
        )
}
