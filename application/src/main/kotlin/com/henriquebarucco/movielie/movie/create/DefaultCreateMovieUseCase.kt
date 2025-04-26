package com.henriquebarucco.movielie.movie.create

import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.create.dto.CreateMovieCommand
import com.henriquebarucco.movielie.movie.create.dto.CreateMovieOutput
import com.henriquebarucco.movielie.movie.enum.Status
import com.henriquebarucco.movielie.movie.vo.ExternalReference
import com.henriquebarucco.movielie.movie.vo.Provider

class DefaultCreateMovieUseCase(
    private val movieGateway: MovieGateway,
) : CreateMovieUseCase() {
    override fun execute(input: CreateMovieCommand): CreateMovieOutput {
        val movie =
            Movie.new(
                title = input.title,
                originalTitle = input.originalTitle,
                overview = input.overview,
                releaseDate = input.releaseDate,
                status = Status.valueOf(input.status),
                externalReference =
                    ExternalReference(
                        provider = Provider.valueOf(input.externalProvider),
                        id = input.externalId,
                    ),
            )

        val aSavedMovie = this.movieGateway.save(movie)

        return CreateMovieOutput(aSavedMovie.id.value)
    }
}
