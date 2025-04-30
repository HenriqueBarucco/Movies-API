package com.henriquebarucco.movielie.shared.exceptions

class ResourceNotFoundException(
    override val message: String,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
