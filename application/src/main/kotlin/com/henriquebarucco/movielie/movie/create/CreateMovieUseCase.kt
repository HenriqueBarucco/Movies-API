package com.henriquebarucco.movielie.movie.create

import com.henriquebarucco.movielie.UseCase
import com.henriquebarucco.movielie.movie.create.dto.CreateMovieCommand
import com.henriquebarucco.movielie.movie.create.dto.CreateMovieOutput

abstract class CreateMovieUseCase : UseCase<CreateMovieCommand, CreateMovieOutput>()
