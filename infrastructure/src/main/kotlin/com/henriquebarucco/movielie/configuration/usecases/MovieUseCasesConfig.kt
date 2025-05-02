package com.henriquebarucco.movielie.configuration.usecases

import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.create.CreateMovieUseCase
import com.henriquebarucco.movielie.movie.create.CreateMovieValidator
import com.henriquebarucco.movielie.movie.create.DefaultCreateMovieUseCase
import com.henriquebarucco.movielie.movie.fetch.search.DefaultSearchMoviesUseCase
import com.henriquebarucco.movielie.movie.fetch.search.SearchMoviesUseCase
import com.henriquebarucco.movielie.movie.fetch.similar.DefaultFetchSimilarMoviesUseCase
import com.henriquebarucco.movielie.movie.fetch.similar.FetchSimilarMoviesUseCase
import com.henriquebarucco.movielie.movie.save.DefaultSaveMovieUseCase
import com.henriquebarucco.movielie.movie.save.SaveMovieUseCase
import com.henriquebarucco.movielie.movie.update.DefaultUpdateMovieUseCase
import com.henriquebarucco.movielie.movie.update.UpdateMovieUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MovieUseCasesConfig(
    private val movieGateway: MovieGateway,
) {
    @Bean
    fun createMovieUseCase(createMovieValidator: CreateMovieValidator): CreateMovieUseCase =
        DefaultCreateMovieUseCase(movieGateway, createMovieValidator)

    @Bean
    fun updateMovieUseCase(): UpdateMovieUseCase = DefaultUpdateMovieUseCase(movieGateway)

    @Bean
    fun saveMovieUseCase(
        updateMovieUseCase: UpdateMovieUseCase,
        createMovieUseCase: CreateMovieUseCase,
    ): SaveMovieUseCase = DefaultSaveMovieUseCase(movieGateway, createMovieUseCase, updateMovieUseCase)

    @Bean
    fun createMovieValidator(): CreateMovieValidator = CreateMovieValidator(movieGateway)

    @Bean
    fun FetchSimilarMoviesUseCase(): FetchSimilarMoviesUseCase = DefaultFetchSimilarMoviesUseCase(movieGateway)

    @Bean
    fun searchMoviesUseCase(): SearchMoviesUseCase = DefaultSearchMoviesUseCase(movieGateway)
}
