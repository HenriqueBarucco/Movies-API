package com.henriquebarucco.movielie.movie

import com.henriquebarucco.movielie.shared.utils.DomainId

data class MovieId(
    val value: String,
) : DomainId() {
    companion object {
        fun unique(): MovieId = MovieId(generate())

        fun with(value: String): MovieId {
            validate<MovieId>(value)
            return MovieId(value)
        }
    }
}
