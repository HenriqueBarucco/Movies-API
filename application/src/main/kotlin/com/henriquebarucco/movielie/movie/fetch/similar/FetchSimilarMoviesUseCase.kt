package com.henriquebarucco.movielie.movie.fetch.similar

import com.henriquebarucco.movielie.UseCase
import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMoviesCommand
import com.henriquebarucco.movielie.movie.fetch.similar.dto.FetchSimilarMoviesOutput

abstract class FetchSimilarMoviesUseCase : UseCase<FetchSimilarMoviesCommand, FetchSimilarMoviesOutput>()
