package com.henriquebarucco.movielie.entrypoint.http.v1.movie.dto

import com.henriquebarucco.movielie.movie.fetch.search.dto.SearchMoviesItemOutput
import com.henriquebarucco.movielie.movie.fetch.search.dto.SearchMoviesOutput

data class SearchMoviesResponse(
    val results: List<SearchMoviesItemResponse>,
) {
    companion object {
        fun fromOutput(output: SearchMoviesOutput) =
            SearchMoviesResponse(
                results = output.results.map { SearchMoviesItemResponse.fromOutput(it) },
            )
    }
}

data class SearchMoviesItemResponse(
    val id: String,
    val title: String,
    val poster: String,
    val checksum: String,
) {
    companion object {
        fun fromOutput(output: SearchMoviesItemOutput) =
            SearchMoviesItemResponse(
                id = output.id,
                title = output.title,
                poster = output.poster,
                checksum = output.checksum,
            )
    }
}
