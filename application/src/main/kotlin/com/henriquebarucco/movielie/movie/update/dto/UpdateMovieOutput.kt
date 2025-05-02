package com.henriquebarucco.movielie.movie.update.dto

import com.henriquebarucco.movielie.movie.Movie

data class UpdateMovieOutput(
    val id: String,
) {
    constructor(movie: Movie) : this(
        id = movie.id.value,
    )
}
