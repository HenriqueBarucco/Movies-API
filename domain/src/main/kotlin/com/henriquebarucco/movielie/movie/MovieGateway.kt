package com.henriquebarucco.movielie.movie

interface MovieGateway {
    fun save(movie: Movie): Movie
}
