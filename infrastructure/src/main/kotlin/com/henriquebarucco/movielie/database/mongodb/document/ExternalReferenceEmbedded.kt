package com.henriquebarucco.movielie.database.mongodb.document

import com.henriquebarucco.movielie.movie.vo.ExternalReference
import com.henriquebarucco.movielie.movie.vo.Provider

data class ExternalReferenceEmbedded(
    val provider: String,
    val id: String,
) {
    fun toDomain(): ExternalReference =
        ExternalReference(
            provider = Provider.valueOf(provider),
            id = id,
        )
}

fun ExternalReference.toExternalReferenceEmbedded(): ExternalReferenceEmbedded =
    ExternalReferenceEmbedded(
        provider = provider.name,
        id = id,
    )
