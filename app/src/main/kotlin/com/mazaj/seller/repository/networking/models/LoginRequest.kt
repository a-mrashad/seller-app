package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String? = null,
    val password: String? = null
)
