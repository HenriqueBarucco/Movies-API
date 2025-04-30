package com.henriquebarucco.movielie.service

import com.henriquebarucco.movielie.database.mongodb.document.MovieDocument
import com.henriquebarucco.movielie.database.mongodb.document.toDocument
import com.henriquebarucco.movielie.database.mongodb.repository.MovieMongodbRepository
import com.henriquebarucco.movielie.filter.Filter
import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.MovieId
import com.henriquebarucco.movielie.movie.vo.ExternalReference
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieMongoRepository: MovieMongodbRepository,
    private val vectorStore: VectorStore,
    private val mongoTemplate: MongoTemplate,
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

    override fun findByFilters(filters: List<Filter>): List<Movie> {
        val criteria = Criteria()

        val andCriteria =
            filters.map { filter ->
                val value = parseValue(filter.value)
                when (filter.operation.lowercase()) {
                    "eq" -> Criteria.where(filter.field).`is`(value)
                    "neq" -> Criteria.where(filter.field).ne(value)
                    "gte" -> Criteria.where(filter.field).gte(value)
                    "lte" -> Criteria.where(filter.field).lte(value)
                    "gt" -> Criteria.where(filter.field).gt(value)
                    "lt" -> Criteria.where(filter.field).lt(value)
                    "regex" -> Criteria.where(filter.field).regex(value.toString(), "i")
                    "in" -> Criteria.where(filter.field).`in`(splitList(value.toString()))
                    "nin" -> Criteria.where(filter.field).nin(splitList(value.toString()))
                    "elemmatch" -> {
                        val subFilters = parseElemMatch(filter.value)
                        val subCriteria = subFilters.map { (k, v) -> Criteria.where(k).`is`(v) }
                        Criteria.where(filter.field).elemMatch(Criteria().andOperator(*subCriteria.toTypedArray()))
                    }
                    else -> throw IllegalArgumentException("Unsupported operation: ${filter.operation}")
                }
            }

        criteria.andOperator(*andCriteria.toTypedArray())

        val query = Query(criteria)
        val moviesDocuments = this.mongoTemplate.find(query, MovieDocument::class.java)

        return moviesDocuments.map { it.toDomain() }
    }

    private fun parseValue(raw: String): Any = raw.toIntOrNull() ?: raw.toDoubleOrNull() ?: raw

    private fun splitList(value: String): List<String> = value.split(",").map { it.trim() }

    private fun parseElemMatch(value: String): Map<String, Any> =
        value
            .split(",")
            .map { it.split(":") }
            .associate { it[0].trim() to it[1].trim() }
}
