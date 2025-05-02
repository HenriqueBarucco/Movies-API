package com.henriquebarucco.movielie.movie.vo

data class Imdb(
    val id: String,
    val url: String,
) {
    constructor(id: String) : this(
        id = id,
        url = "https://www.imdb.com/title/$id",
    )
}
