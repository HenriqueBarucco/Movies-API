package com.henriquebarucco.movielie.movie.fetch.similar.dto

import com.henriquebarucco.movielie.movie.Movie

data class FetchSimilarMoviesOutput(
    val results: List<FetchSimilarMovieOutput>,
)

data class FetchSimilarMovieOutput(
    val id: String,
    val title: String,
    val overview: String,
) {
    companion object {
        fun fromDomain(movie: Movie) =
            FetchSimilarMovieOutput(
                id = movie.id.value,
                title = movie.title,
                overview = movie.overview,
            )
    }
}
