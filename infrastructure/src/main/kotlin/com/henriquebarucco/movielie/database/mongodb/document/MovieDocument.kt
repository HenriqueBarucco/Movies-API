package com.henriquebarucco.movielie.database.mongodb.document

import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieId
import com.henriquebarucco.movielie.movie.enum.Status
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "movies")
data class MovieDocument(
    val id: String,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: LocalDate,
    val status: String,
) {
    fun toDomain(): Movie =
        Movie(
            id = MovieId.with(id),
            title = title,
            originalTitle = originalTitle,
            overview = overview,
            releaseDate = releaseDate,
            status = Status.valueOf(status),
            externalReferences = emptySet(),
        )
}

fun Movie.toDocument(): MovieDocument =
    MovieDocument(
        id = this.id.value,
        title = this.title,
        originalTitle = this.originalTitle,
        overview = this.overview,
        releaseDate = this.releaseDate,
        status = this.status.name,
    )
