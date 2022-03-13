package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: Long? = null,
    var name: String? = null
)
