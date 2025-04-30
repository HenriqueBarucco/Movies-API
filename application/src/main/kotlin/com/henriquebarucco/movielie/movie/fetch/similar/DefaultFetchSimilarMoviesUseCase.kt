package com.henriquebarucco.movielie.movie.fetch.similar

import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.MovieId
import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMovieOutput
import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMoviesCommand
import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMoviesOutput
import com.henriquebarucco.movielie.shared.exceptions.ResourceNotFoundException

class DefaultFetchSimilarMoviesUseCase(
    private val movieGateway: MovieGateway,
) : FetchSimilarMoviesUseCase() {
    override fun execute(input: FetchSimilarMoviesCommand): FetchSimilarMoviesOutput {
        val movie = this.movieGateway.findById(MovieId.with(input.movieId)) ?: throw ResourceNotFoundException("Movie not found")

        val movies = this.movieGateway.findSimilar(movie)

        return FetchSimilarMoviesOutput(
            results = movies.map { FetchSimilarMovieOutput.fromDomain(it) },
        )
    }
}
