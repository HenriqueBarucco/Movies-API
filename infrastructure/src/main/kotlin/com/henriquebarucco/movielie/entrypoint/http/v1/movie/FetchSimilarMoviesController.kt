package com.henriquebarucco.movielie.entrypoint.http.v1.movie

import com.henriquebarucco.movielie.entrypoint.http.v1.movie.dto.FetchSimilarMovieResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(value = ["/v1/movies/similar"])
interface FetchSimilarMoviesController {
    @GetMapping(value = ["/{id}"])
    fun fetchSimilarMovies(
        @PathVariable id: String,
    ): ResponseEntity<FetchSimilarMovieResponse>
}
