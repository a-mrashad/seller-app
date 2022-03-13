package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    @SerialName("errors")
    val errors: List<String?>? = null
)
