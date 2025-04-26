package com.henriquebarucco.movielie.shared.exceptions

data class InvalidStringValueException(
    override val message: String,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
