package com.henriquebarucco.movielie.movie.save

import com.henriquebarucco.movielie.filter.Filter
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.create.CreateMovieUseCase
import com.henriquebarucco.movielie.movie.create.dto.CreateMovieCommand
import com.henriquebarucco.movielie.movie.save.dto.SaveMovieCommand
import com.henriquebarucco.movielie.movie.save.dto.SaveMovieOutput
import com.henriquebarucco.movielie.movie.update.UpdateMovieUseCase
import com.henriquebarucco.movielie.movie.update.dto.UpdateMovieCommand
import com.henriquebarucco.movielie.movie.vo.ExternalReference
import com.henriquebarucco.movielie.movie.vo.Provider

class DefaultSaveMovieUseCase(
    private val movieGateway: MovieGateway,
    private val createMovieUseCase: CreateMovieUseCase,
    private val updateMovieUseCase: UpdateMovieUseCase,
) : SaveMovieUseCase() {
    override fun execute(input: SaveMovieCommand): SaveMovieOutput {
        val (externalId, externalProvider) = input

        val existMovieByRef = this.movieGateway.findByExternalReference(ExternalReference(externalId, Provider.valueOf(externalProvider)))

        val originalTitleFilter = Filter("originalTitle", "eq", input.originalTitle)
        val releaseDateFilter = Filter("releaseDate", "eq", input.releaseDate)
        val existMovieByTitleAndReleaseDate = this.movieGateway.findByFilters(listOf(originalTitleFilter, releaseDateFilter))

        if (existMovieByRef != null || existMovieByTitleAndReleaseDate.isNotEmpty()) {
            val movie = existMovieByRef ?: existMovieByTitleAndReleaseDate.first()

            if (movie.checksum() == input.checksum()) {
                return SaveMovieOutput(movie.id.value, "No need to update the movie, the checksum is the same")
            }

            this.updateMovieUseCase
                .execute(
                    UpdateMovieCommand(
                        id = movie.id.value,
                        externalId = externalId,
                        externalProvider = externalProvider,
                        title = input.title,
                        originalTitle = input.originalTitle,
                        originalLanguage = input.originalLanguage,
                        imdbId = input.imdbId,
                        releaseDate = input.releaseDate,
                        poster = input.poster,
                        backdrop = input.backdrop,
                        overview = input.overview,
                        status = input.status,
                        duration = input.duration,
                        genres = input.genres,
                        keywords = input.keywords,
                        videos = input.videos,
                    ),
                )

            return SaveMovieOutput(movie.id.value, "Movie updated successfully")
        } else {
            val createdMovie =
                this.createMovieUseCase
                    .execute(
                        CreateMovieCommand(
                            externalId = externalId,
                            externalProvider = externalProvider,
                            title = input.title,
                            originalTitle = input.originalTitle,
                            originalLanguage = input.originalLanguage,
                            imdbId = input.imdbId,
                            releaseDate = input.releaseDate,
                            poster = input.poster,
                            backdrop = input.backdrop,
                            overview = input.overview,
                            status = input.status,
                            duration = input.duration,
                            genres = input.genres,
                            keywords = input.keywords,
                            videos = input.videos,
                        ),
                    )

            return SaveMovieOutput(createdMovie.id, "Movie created successfully")
        }
    }
}
