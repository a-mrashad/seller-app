package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionsDetailsResponse(
    val id: Long,
    val type: Int,
    @SerialName("type_label")
    val typeLabel: String,
    @SerialName("items_count")
    val itemsCount: Int = 0,
    @SerialName("total_price")
    val totalPrice: Double? = null,
    val items: List<SubscriptionOrder>
)

@Serializable
data class SubscriptionOrder(
    val id: Long,
    val quantity: Int? = null,
    val title: String,
    val note: String? = null,
    val total: Double? = null,
    val addOns: MutableList<OrderAddOns>? = null,
    val options: MutableList<OrderOptions>? = null,
    val variants: MutableList<OrderVariants>? = null
)
