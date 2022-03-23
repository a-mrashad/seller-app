package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody (
    val username: String,
    val password: String
)