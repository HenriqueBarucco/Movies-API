package com.henriquebarucco.movielie.movie

import com.henriquebarucco.movielie.movie.vo.ExternalReference

interface MovieGateway {
    fun save(movie: Movie): Movie

    fun findByExternalReference(externalReference: ExternalReference): Movie?
}
