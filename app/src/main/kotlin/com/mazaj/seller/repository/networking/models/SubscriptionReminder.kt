package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionReminder(
    @SerialName("order_number")
    val orderNumber: String,
    @SerialName("items_number")
    val itemsNumber: Int,
    @SerialName("pickup_time")
    val pickupTime: String
)

@Serializable
data class SubscriptionReminderData(
    val data: List<SubscriptionReminder>
)
