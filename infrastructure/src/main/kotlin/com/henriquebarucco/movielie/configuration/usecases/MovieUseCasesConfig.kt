package com.henriquebarucco.movielie.configuration.usecases

import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.create.CreateMovieUseCase
import com.henriquebarucco.movielie.movie.create.CreateMovieValidator
import com.henriquebarucco.movielie.movie.create.DefaultCreateMovieUseCase
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
    fun createMovieValidator(): CreateMovieValidator = CreateMovieValidator(movieGateway)
}
