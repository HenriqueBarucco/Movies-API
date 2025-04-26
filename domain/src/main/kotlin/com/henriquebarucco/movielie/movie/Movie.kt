package com.henriquebarucco.movielie.movie

import com.henriquebarucco.movielie.movie.enum.Status
import com.henriquebarucco.movielie.movie.vo.ExternalReference
import java.time.LocalDate

class Movie(
    val id: MovieId,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: LocalDate,
    val status: Status,
    val externalReferences: Set<ExternalReference>,
) {
    companion object {
        fun new(
            title: String,
            originalTitle: String,
            overview: String,
            releaseDate: LocalDate,
            status: Status,
            externalReference: ExternalReference,
        ): Movie =
            Movie(
                id = MovieId.unique(),
                title = title,
                originalTitle = originalTitle,
                overview = overview,
                releaseDate = releaseDate,
                status = status,
                externalReferences = setOf(externalReference),
            )
    }
}
