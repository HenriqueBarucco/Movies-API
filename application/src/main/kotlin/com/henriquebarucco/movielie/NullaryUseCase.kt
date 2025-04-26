package com.henriquebarucco.movielie

abstract class NullaryUseCase<OUT> {
    abstract fun execute(): OUT
}
