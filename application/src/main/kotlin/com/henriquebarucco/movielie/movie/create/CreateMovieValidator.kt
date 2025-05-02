package com.henriquebarucco.movielie.movie.create

import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.validators.ExternalReferenceConflictValidator
import com.henriquebarucco.movielie.movie.validators.MovieValidator
import com.henriquebarucco.movielie.movie.validators.OriginalTitleAndReleaseDateConflictValidator

class CreateMovieValidator(
    private val movieGateway: MovieGateway,
) {
    private val validators: List<MovieValidator> =
        listOf(
            ExternalReferenceConflictValidator(movieGateway),
            OriginalTitleAndReleaseDateConflictValidator(movieGateway),
        )

    fun validate(movie: Movie) {
        this.validators.forEach { it.validate(movie) }
    }
}
