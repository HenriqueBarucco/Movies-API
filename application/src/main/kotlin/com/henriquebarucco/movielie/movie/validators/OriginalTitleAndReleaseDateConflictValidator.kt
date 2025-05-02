package com.henriquebarucco.movielie.movie.validators

import com.henriquebarucco.movielie.filter.Filter
import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.shared.exceptions.ResourceConflictException

class OriginalTitleAndReleaseDateConflictValidator(
    private val movieGateway: MovieGateway,
) : MovieValidator {
    override fun validate(movie: Movie) {
        movie.externalReferences
            .forEach { externalReference ->
                val existingMovie = this.movieGateway.findByExternalReference(externalReference)
                if (existingMovie != null) {
                    throw ResourceConflictException(
                        "Movie with external reference ${externalReference.provider} already exists: ${existingMovie.id}",
                    )
                }
            }
        val originalTitleFilter = Filter("originalTitle", "eq", movie.originalTitle)
        val releaseDateFilter = Filter("releaseDate", "eq", movie.releaseDate.toString())

        val movies = this.movieGateway.findByFilters(listOf(originalTitleFilter, releaseDateFilter))

        if (movies.isNotEmpty()) {
            throw ResourceConflictException(
                "Movie with original title ${movie.originalTitle} and release date ${movie.releaseDate} already exists",
            )
        }
    }
}
