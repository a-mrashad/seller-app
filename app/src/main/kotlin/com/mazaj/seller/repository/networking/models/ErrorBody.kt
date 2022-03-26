package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    val errors: ErrorItem? = null,
    val message: String? = null
)

@Serializable
data class ErrorItem(
    val username: List<String?>? = null
)
