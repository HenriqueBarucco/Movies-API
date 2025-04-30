package com.henriquebarucco.movielie.movie.fetch.search

import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.fetch.search.dto.SearchMoviesCommand
import com.henriquebarucco.movielie.movie.fetch.search.dto.SearchMoviesItemOutput
import com.henriquebarucco.movielie.movie.fetch.search.dto.SearchMoviesOutput

class DefaultSearchMoviesUseCase(
    private val movieGateway: MovieGateway,
) : SearchMoviesUseCase() {
    override fun execute(input: SearchMoviesCommand): SearchMoviesOutput {
        val movies = this.movieGateway.findByFilters(input.filters)

        return SearchMoviesOutput(
            results = movies.map { SearchMoviesItemOutput.fromDomain(it) },
        )
    }
}
