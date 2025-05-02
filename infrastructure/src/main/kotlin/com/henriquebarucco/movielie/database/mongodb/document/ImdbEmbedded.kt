package com.henriquebarucco.movielie.database.mongodb.document

import com.henriquebarucco.movielie.movie.vo.Imdb

data class ImdbEmbedded(
    val id: String,
    val url: String,
) {
    constructor(imdb: Imdb) : this(imdb.id, imdb.url)

    fun toDomain() = Imdb(id, url)
}
