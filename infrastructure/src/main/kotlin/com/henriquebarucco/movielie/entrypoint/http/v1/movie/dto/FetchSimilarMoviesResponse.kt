package com.henriquebarucco.movielie.entrypoint.http.v1.movie.dto

import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMoviesItemOutput
import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMoviesOutput

data class FetchSimilarMovieResponse(
    val results: List<FetchSimilarMovieItemResponse>,
) {
    companion object {
        fun fromOutput(output: FetchSimilarMoviesOutput) =
            FetchSimilarMovieResponse(
                results = output.results.map { FetchSimilarMovieItemResponse.fromOutput(it) },
            )
    }
}

data class FetchSimilarMovieItemResponse(
    val id: String,
    val title: String,
    val overview: String,
) {
    companion object {
        fun fromOutput(output: FetchSimilarMoviesItemOutput) =
            FetchSimilarMovieItemResponse(
                id = output.id,
                title = output.title,
                overview = output.overview,
            )
    }
}
