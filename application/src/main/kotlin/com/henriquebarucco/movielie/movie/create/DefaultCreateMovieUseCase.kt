package com.henriquebarucco.movielie.movie.create

import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.create.dto.CreateMovieCommand
import com.henriquebarucco.movielie.movie.create.dto.CreateMovieOutput

class DefaultCreateMovieUseCase(
    private val movieGateway: MovieGateway,
    private val createMovieValidator: CreateMovieValidator,
) : CreateMovieUseCase() {
    override fun execute(input: CreateMovieCommand): CreateMovieOutput {
        val movie =
            Movie.new(
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

        this.createMovieValidator.validate(movie)

        val aSavedMovie = this.movieGateway.save(movie)
        return CreateMovieOutput(aSavedMovie)
    }
}
