package com.henriquebarucco.movielie.movie.update

import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.MovieId
import com.henriquebarucco.movielie.movie.update.dto.UpdateMovieCommand
import com.henriquebarucco.movielie.movie.update.dto.UpdateMovieOutput
import com.henriquebarucco.movielie.shared.exceptions.ResourceNotFoundException

class DefaultUpdateMovieUseCase(
    private val movieGateway: MovieGateway,
) : UpdateMovieUseCase() {
    override fun execute(input: UpdateMovieCommand): UpdateMovieOutput {
        val movie =
            this.movieGateway.findById(MovieId.with(input.id)) ?: throw ResourceNotFoundException(
                "Movie with id ${input.id} not found",
            )

        movie.update(
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
            externalReferenceId = input.externalId,
            externalReferenceProvider = input.externalProvider,
            videos = input.videos,
            genres = input.genres,
            keywords = input.keywords,
        )

        val updatedMovie = this.movieGateway.save(movie)

        return UpdateMovieOutput(updatedMovie)
    }
}
