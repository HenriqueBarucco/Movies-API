package com.henriquebarucco.movielie.database.mongodb.document

import com.henriquebarucco.movielie.movie.enum.VideoType
import com.henriquebarucco.movielie.movie.vo.Url
import com.henriquebarucco.movielie.movie.vo.Video

data class VideoEmbedded(
    val id: String,
    val name: String,
    val url: String,
    val type: String,
) {
    fun toDomain() =
        Video(
            id = id,
            name = name,
            url = Url(url),
            type = VideoType.valueOf(type),
        )
}

fun Video.toVideoEmbedded() =
    VideoEmbedded(
        id = id,
        name = name,
        url = url.value,
        type = type.name,
    )
