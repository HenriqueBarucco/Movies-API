package com.henriquebarucco.movielie.entrypoint.http.v1.movie.impl

import com.henriquebarucco.movielie.entrypoint.http.v1.movie.SearchMoviesController
import com.henriquebarucco.movielie.entrypoint.http.v1.movie.dto.SearchMoviesResponse
import com.henriquebarucco.movielie.filter.Filter
import com.henriquebarucco.movielie.movie.fetch.search.SearchMoviesUseCase
import com.henriquebarucco.movielie.movie.fetch.search.dto.SearchMoviesCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchMoviesControllerImpl(
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : SearchMoviesController {
    override fun searchMovies(allParams: Map<String, String>): ResponseEntity<SearchMoviesResponse> {
        if (allParams.isEmpty()) {
            throw IllegalArgumentException("At least one filter must be provided")
        }

        val filters =
            allParams.map { (key, value) ->
                val parts = key.split(":")
                val field = parts[0]
                val operation = if (parts.size > 1) parts[1] else "eq"
                Filter(field, operation, value)
            }

        val movies = this.searchMoviesUseCase.execute(SearchMoviesCommand(filters))

        val response = SearchMoviesResponse.fromOutput(movies)
        return ResponseEntity.ok(response)
    }
}
