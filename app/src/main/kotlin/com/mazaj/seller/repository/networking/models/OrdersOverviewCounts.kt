package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrdersOverviewCounts(
    val total: String,
    val status: String,
    @SerialName("status_label")
    val statusLabel: String
)
