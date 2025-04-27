package com.henriquebarucco.movielie.movie.validators

import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.shared.exceptions.ResourceConflictException

class ExternalReferenceConflictValidator(
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
    }
}
