package com.henriquebarucco.movielie.movie.create.dto

import com.henriquebarucco.movielie.movie.Movie

data class CreateMovieOutput(
    val id: String,
) {
    constructor(movie: Movie) : this(
        id = movie.id.value,
    )
}
