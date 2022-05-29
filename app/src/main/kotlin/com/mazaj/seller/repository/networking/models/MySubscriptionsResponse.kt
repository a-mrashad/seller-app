package com.mazaj.seller.repository.networking.models

import com.mazaj.seller.ui.shared.pagination.ListItem
import com.mazaj.seller.utils.DateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.joda.time.DateTime

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
    @SerialName("order_number") val orderNumber: String,
    @SerialName("delivery_at")
    val deliveryAt: String?= null
) : ListItem()
