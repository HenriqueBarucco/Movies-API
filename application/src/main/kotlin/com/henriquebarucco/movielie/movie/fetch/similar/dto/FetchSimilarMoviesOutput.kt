package com.henriquebarucco.movielie.movie.fetch.similar.dto

import com.henriquebarucco.movielie.movie.Movie

data class FetchSimilarMoviesOutput(
    val results: List<FetchSimilarMoviesItemOutput>,
)

data class FetchSimilarMoviesItemOutput(
    val id: String,
    val title: String,
    val poster: String,
) {
    companion object {
        fun fromDomain(movie: Movie) =
            FetchSimilarMoviesItemOutput(
                id = movie.id.value,
                title = movie.title,
                poster = movie.poster.value,
            )
    }
}
