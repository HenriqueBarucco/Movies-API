package com.henriquebarucco.movielie.entrypoint.http.v1.movie

import com.henriquebarucco.movielie.entrypoint.http.v1.movie.dto.SearchMoviesResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping(value = ["/v1/movies"])
interface SearchMoviesController {
    @GetMapping(value = ["/search"])
    fun searchMovies(
        @RequestParam allParams: Map<String, String>,
    ): ResponseEntity<SearchMoviesResponse>
}
