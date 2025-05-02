package com.henriquebarucco.movielie.movie

import com.henriquebarucco.movielie.movie.enum.Language
import com.henriquebarucco.movielie.movie.enum.Status
import com.henriquebarucco.movielie.movie.vo.ExternalReference
import com.henriquebarucco.movielie.movie.vo.Imdb
import com.henriquebarucco.movielie.movie.vo.Provider
import com.henriquebarucco.movielie.movie.vo.Url
import com.henriquebarucco.movielie.movie.vo.Video
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.time.Instant
import java.time.LocalDate

class Movie(
    val id: MovieId,
    var title: String,
    var originalTitle: String,
    var originalLanguage: Language,
    var imdb: Imdb?,
    var releaseDate: LocalDate,
    var poster: Url,
    var backdrop: Url?,
    var overview: String,
    var status: Status,
    var duration: Int,
    var videos: List<Video>,
    var genres: Set<String>,
    var keywords: Set<String>,
    var externalReferences: Set<ExternalReference>,
    val createdAt: Instant,
    var updatedAt: Instant?,
    var deletedAt: Instant?,
) {
    companion object {
        fun new(
            title: String,
            originalTitle: String,
            originalLanguage: String,
            imdbId: String?,
            releaseDate: String,
            poster: String,
            backdrop: String?,
            overview: String,
            status: String,
            duration: Int,
            externalReferenceId: String,
            externalReferenceProvider: String,
            videos: List<Video>,
            genres: List<String>,
            keywords: List<String>,
        ): Movie =
            Movie(
                id = MovieId.unique(),
                title = title,
                originalTitle = originalTitle,
                originalLanguage = Language.valueOf(originalLanguage),
                imdb = imdbId?.let { Imdb(it) },
                releaseDate = LocalDate.parse(releaseDate),
                poster = Url(poster),
                backdrop = backdrop?.let { Url(it) },
                overview = overview,
                status = Status.valueOf(status),
                duration = duration,
                videos = videos,
                genres = genres.toSet(),
                keywords = keywords.toSet(),
                externalReferences = setOf(ExternalReference(externalReferenceId, Provider.valueOf(externalReferenceProvider))),
                createdAt = Instant.now(),
                updatedAt = null,
                deletedAt = null,
            )
    }

    fun checksum(): String {
        val input = "$title$originalTitle${originalLanguage.name}${imdb?.id}$releaseDate${poster.value}${backdrop?.value}$overview${status.name}$duration$videos$genres$keywords"
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray(StandardCharsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun update(
        title: String,
        originalTitle: String,
        originalLanguage: String,
        imdbId: String?,
        releaseDate: String,
        poster: String,
        backdrop: String?,
        overview: String,
        status: String,
        duration: Int,
        externalReferenceId: String,
        externalReferenceProvider: String,
        videos: List<Video>,
        genres: List<String>,
        keywords: List<String>,
    ) {
        this.title = title
        this.originalTitle = originalTitle
        this.originalLanguage = Language.valueOf(originalLanguage)
        this.imdb = imdbId?.let { Imdb(it) }
        this.releaseDate = LocalDate.parse(releaseDate)
        this.poster = Url(poster)
        this.backdrop = backdrop?.let { Url(it) }
        this.overview = overview
        this.status = Status.valueOf(status)
        this.duration = duration
        this.videos = videos
        this.genres = genres.toSet()
        this.keywords = keywords.toSet()
        this.externalReferences =
            this.externalReferences.plus(ExternalReference(externalReferenceId, Provider.valueOf(externalReferenceProvider)))
        this.updatedAt = Instant.now()
    }
}
