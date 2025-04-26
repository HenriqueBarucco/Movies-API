package com.henriquebarucco.movielie.movie.create.dto

import java.time.LocalDate

data class CreateMovieCommand(
    val title: String,
    val originalTitle: String,
    val poster: String,
    val overview: String,
    val releaseDate: LocalDate,
    val status: String,
    val externalProvider: String,
    val externalId: String,
)
