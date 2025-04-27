package com.henriquebarucco.movielie.database.mongodb

import com.henriquebarucco.movielie.database.mongodb.document.toDocument
import com.henriquebarucco.movielie.database.mongodb.repository.MovieMongodbRepository
import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.vo.ExternalReference
import org.springframework.stereotype.Service

@Service
class MovieRepository(
    private val movieMongoRepository: MovieMongodbRepository,
) : MovieGateway {
    override fun save(movie: Movie): Movie {
        val movieDocument = movie.toDocument()
        val savedMovieDocument = this.movieMongoRepository.save(movieDocument)

        return savedMovieDocument.toDomain()
    }

    override fun findByExternalReference(externalReference: ExternalReference): Movie? {
        val movieDocument =
            this.movieMongoRepository.findByExternalReferencesProviderAndExternalReferencesId(
                provider = externalReference.provider.name,
                id = externalReference.id,
            )

        return movieDocument?.toDomain()
    }
}
