package com.henriquebarucco.movielie

abstract class UseCase<IN, OUT> {
    abstract fun execute(input: IN): OUT
}
