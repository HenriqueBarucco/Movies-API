package com.henriquebarucco.movielie.movie.fetch.search

import com.henriquebarucco.movielie.UseCase
import com.henriquebarucco.movielie.movie.fetch.search.dto.SearchMoviesCommand
import com.henriquebarucco.movielie.movie.fetch.search.dto.SearchMoviesOutput

abstract class SearchMoviesUseCase : UseCase<SearchMoviesCommand, SearchMoviesOutput>()
