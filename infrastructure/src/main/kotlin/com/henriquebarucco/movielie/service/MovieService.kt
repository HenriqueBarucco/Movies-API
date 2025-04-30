package com.henriquebarucco.movielie.service

import com.henriquebarucco.movielie.database.mongodb.document.toDocument
import com.henriquebarucco.movielie.database.mongodb.repository.MovieMongodbRepository
import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.MovieId
import com.henriquebarucco.movielie.movie.vo.ExternalReference
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieMongoRepository: MovieMongodbRepository,
    private val vectorStore: VectorStore,
) : MovieGateway {
    override fun save(movie: Movie): Movie {
        val movieDocument = movie.toDocument()
        val savedMovieDocument = this.movieMongoRepository.save(movieDocument)

        saveVector(movie)

        return savedMovieDocument.toDomain()
    }

    private fun saveVector(movie: Movie) {
        val text = "${movie.title} ${movie.overview}"
        val document =
            Document(
                movie.id.value,
                text,
                mapOf(
                    "releaseDate" to movie.releaseDate.toString(),
                    "title" to movie.title,
                    "originalTitle" to movie.originalTitle,
                ),
            )

        this.vectorStore.add(listOf(document))
    }

    override fun findByExternalReference(externalReference: ExternalReference): Movie? {
        val movieDocument =
            this.movieMongoRepository.findByExternalReferencesProviderAndExternalReferencesId(
                provider = externalReference.provider.name,
                id = externalReference.id,
            )

        return movieDocument?.toDomain()
    }

    override fun findById(movieId: MovieId): Movie? =
        this.movieMongoRepository
            .findById(movieId.value)
            .map { it.toDomain() }
            .orElse(null)

    override fun findSimilar(movie: Movie): List<Movie> {
        val similarMovies =
            this.vectorStore.similaritySearch(
                SearchRequest
                    .builder()
                    .query("${movie.title} ${movie.overview}")
                    .topK(55)
                    .similarityThreshold(0.7)
                    .build(),
            )

        if (similarMovies == null || similarMovies.isEmpty()) {
            return emptyList()
        }

        val moviesIds = similarMovies.map { it.id }

        val moviesDocuments = this.movieMongoRepository.findAllById(moviesIds)

        return moviesDocuments.map { it.toDomain() }
    }
}
