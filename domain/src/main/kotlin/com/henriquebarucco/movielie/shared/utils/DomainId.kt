package com.henriquebarucco.movielie.shared.utils

import com.henriquebarucco.movielie.shared.exceptions.InvalidStringValueException
import java.util.UUID

abstract class DomainId {
    companion object {
        fun generate() = UUID.randomUUID().toString()

        inline fun <reified T : Any> validate(value: String) {
            if (value.isEmpty()) throw InvalidStringValueException("Invalid value for ${T::class.simpleName}.")

            try {
                UUID.fromString(value)
            } catch (e: IllegalArgumentException) {
                throw InvalidStringValueException("Invalid UUID format for ${T::class.simpleName}.", e)
            }
        }
    }
}
