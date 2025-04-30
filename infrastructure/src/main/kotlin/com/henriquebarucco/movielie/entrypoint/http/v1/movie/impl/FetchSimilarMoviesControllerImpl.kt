package com.henriquebarucco.movielie.entrypoint.http.v1.movie.impl

import com.henriquebarucco.movielie.entrypoint.http.v1.movie.FetchSimilarMoviesController
import com.henriquebarucco.movielie.entrypoint.http.v1.movie.dto.FetchSimilarMovieResponse
import com.henriquebarucco.movielie.movie.fetch.similar.FetchSimilarMoviesUseCase
import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMoviesCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class FetchSimilarMoviesControllerImpl(
    private val fetchSimilarMoviesUseCase: FetchSimilarMoviesUseCase,
) : FetchSimilarMoviesController {
    override fun fetchSimilarMovies(id: String): ResponseEntity<FetchSimilarMovieResponse> {
        val similarMovies = this.fetchSimilarMoviesUseCase.execute(FetchSimilarMoviesCommand(id))

        val response = FetchSimilarMovieResponse.fromOutput(similarMovies)
        return ResponseEntity.ok(response)
    }
}
