package com.henriquebarucco.movielie.movie.create.dto

import com.henriquebarucco.movielie.movie.vo.Video

data class CreateMovieCommand(
    val externalId: String,
    val externalProvider: String,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val imdbId: String?,
    val releaseDate: String,
    val poster: String,
    val backdrop: String?,
    val overview: String,
    val status: String,
    val duration: Int,
    val genres: List<String>,
    val keywords: List<String>,
    val videos: List<Video>,
)
