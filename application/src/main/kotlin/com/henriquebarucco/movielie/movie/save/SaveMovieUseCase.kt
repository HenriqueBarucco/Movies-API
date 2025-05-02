package com.henriquebarucco.movielie.movie.save

import com.henriquebarucco.movielie.UseCase
import com.henriquebarucco.movielie.movie.save.dto.SaveMovieCommand
import com.henriquebarucco.movielie.movie.save.dto.SaveMovieOutput

abstract class SaveMovieUseCase : UseCase<SaveMovieCommand, SaveMovieOutput>()
