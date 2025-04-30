package com.henriquebarucco.movielie.movie.fetch.search.dto

import com.henriquebarucco.movielie.movie.Movie

class SearchMoviesOutput(
    val results: List<SearchMoviesItemOutput>,
)

data class SearchMoviesItemOutput(
    val id: String,
    val title: String,
    val overview: String,
) {
    companion object {
        fun fromDomain(movie: Movie) =
            SearchMoviesItemOutput(
                id = movie.id.value,
                title = movie.title,
                overview = movie.overview,
            )
    }
}
