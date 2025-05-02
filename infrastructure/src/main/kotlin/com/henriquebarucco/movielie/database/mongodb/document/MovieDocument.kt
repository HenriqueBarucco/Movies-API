package com.henriquebarucco.movielie.database.mongodb.document

import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieId
import com.henriquebarucco.movielie.movie.enum.Language
import com.henriquebarucco.movielie.movie.enum.Status
import com.henriquebarucco.movielie.movie.vo.Url
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDate

@Document(collection = "movies")
data class MovieDocument(
    val id: String,
    var title: String,
    var originalTitle: String,
    var originalLanguage: String,
    var imdb: ImdbEmbedded?,
    var releaseDate: String,
    var poster: String,
    var backdrop: String?,
    var overview: String,
    var status: String,
    var duration: Int,
    var videos: List<VideoEmbedded>,
    var genres: List<String>,
    var keywords: List<String>,
    var externalReferences: List<ExternalReferenceEmbedded>,
    val createdAt: Instant,
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null,
) {
    fun toDomain(): Movie =
        Movie(
            id = MovieId.with(id),
            title = title,
            originalTitle = originalTitle,
            originalLanguage = Language.valueOf(originalLanguage),
            imdb = imdb?.toDomain(),
            releaseDate = LocalDate.parse(releaseDate),
            poster = Url(poster),
            backdrop = backdrop?.let { Url(it) },
            overview = overview,
            status = Status.valueOf(status),
            duration = duration,
            videos = videos.map { it.toDomain() },
            genres = genres.toSet(),
            keywords = keywords.toSet(),
            externalReferences = externalReferences.map { it.toDomain() }.toSet(),
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
        )
}

fun Movie.toDocument(): MovieDocument =
    MovieDocument(
        id = this.id.value,
        title = this.title,
        originalTitle = this.originalTitle,
        originalLanguage = this.originalLanguage.name,
        imdb = this.imdb?.let { ImdbEmbedded(it) },
        releaseDate = this.releaseDate.toString(),
        poster = this.poster.value,
        backdrop = this.backdrop?.value,
        overview = this.overview,
        status = this.status.name,
        duration = this.duration,
        videos = this.videos.map { it.toVideoEmbedded() },
        genres = this.genres.toList(),
        keywords = this.keywords.toList(),
        externalReferences = this.externalReferences.map { it.toExternalReferenceEmbedded() },
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deletedAt = this.deletedAt,
    )
