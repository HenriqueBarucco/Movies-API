package com.henriquebarucco.movielie.movie.fetch.search.dto

import com.henriquebarucco.movielie.movie.Movie

class SearchMoviesOutput(
    val results: List<SearchMoviesItemOutput>,
)

data class SearchMoviesItemOutput(
    val id: String,
    val title: String,
    val poster: String,
    val checksum: String,
) {
    companion object {
        fun fromDomain(movie: Movie) =
            SearchMoviesItemOutput(
                id = movie.id.value,
                title = movie.title,
                poster = movie.poster.value,
                checksum = movie.checksum(),
            )
    }
}
