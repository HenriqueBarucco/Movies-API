package com.henriquebarucco.movielie.entrypoint.http.v1.movie.dto

import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMovieOutput
import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMoviesOutput

data class FetchSimilarMovieResponse(
    val results: List<FetchSimilarMovieResponseItem>,
) {
    companion object {
        fun fromOutput(output: FetchSimilarMoviesOutput) =
            FetchSimilarMovieResponse(
                results = output.results.map { FetchSimilarMovieResponseItem.fromDomain(it) },
            )
    }
}

data class FetchSimilarMovieResponseItem(
    val id: String,
    val title: String,
    val overview: String,
) {
    companion object {
        fun fromDomain(movie: FetchSimilarMovieOutput) =
            FetchSimilarMovieResponseItem(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
            )
    }
}
