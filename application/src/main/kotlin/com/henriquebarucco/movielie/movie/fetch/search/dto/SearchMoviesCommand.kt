package com.henriquebarucco.movielie.movie.fetch.search.dto

import com.henriquebarucco.movielie.filter.Filter

data class SearchMoviesCommand(
    val filters: List<Filter>,
)
