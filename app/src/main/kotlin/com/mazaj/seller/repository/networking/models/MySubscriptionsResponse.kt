package com.mazaj.seller.repository.networking.models

import com.mazaj.seller.ui.shared.pagination.ListItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MySubscriptionsResponse(
    val data: List<MySubscriptionData>
)

@Serializable
data class MySubscriptionData(
    val id: Long,
    val status: Int,
    @SerialName("status_label")
    val statusLabel: String,
    @SerialName("items_count")
    val itemsCount: Int,
    val subscriptions: List<SubscriptionsJobDetails>
) : ListItem()
