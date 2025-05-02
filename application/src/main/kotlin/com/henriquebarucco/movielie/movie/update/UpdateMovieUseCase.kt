package com.henriquebarucco.movielie.movie.update

import com.henriquebarucco.movielie.UseCase
import com.henriquebarucco.movielie.movie.update.dto.UpdateMovieCommand
import com.henriquebarucco.movielie.movie.update.dto.UpdateMovieOutput

abstract class UpdateMovieUseCase : UseCase<UpdateMovieCommand, UpdateMovieOutput>()
