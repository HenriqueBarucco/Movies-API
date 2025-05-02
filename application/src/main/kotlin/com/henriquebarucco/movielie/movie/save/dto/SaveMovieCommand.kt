package com.henriquebarucco.movielie.movie.save.dto

import com.henriquebarucco.movielie.movie.vo.Video
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

data class SaveMovieCommand(
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
) {
    fun checksum(): String {
        val input = "$title$originalTitle${originalLanguage}${imdbId}$releaseDate${poster}${backdrop}$overview${status}$duration$videos$genres$keywords"
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray(StandardCharsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
