package com.henriquebarucco.movielie

abstract class UnitUseCase<IN> {
    abstract fun execute(input: IN)
}
