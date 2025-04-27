package com.henriquebarucco.movielie.movie.validators

import com.henriquebarucco.movielie.movie.Movie

interface MovieValidator {
    fun validate(movie: Movie)
}
