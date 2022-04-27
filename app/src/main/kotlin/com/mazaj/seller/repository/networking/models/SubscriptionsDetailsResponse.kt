package com.mazaj.seller.repository.networking.models

import com.mazaj.seller.utils.DateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Serializable
data class SubscriptionsDetailsResponse(
    val id: Long,
    val type: Int,
    @SerialName("is_accepted")
    val isAccepted: Boolean? = null,
    @SerialName("type_label")
    val typeLabel: String,
    @SerialName("items_count")
    val itemsCount: Int = 0,
    @SerialName("total_price")
    val totalPrice: Double? = null,
    @SerialName("payment_status_label")
    val paymentStatusLabel: String,
    val items: List<SubscriptionOrder>,
    val subscriptions: List<SubscriptionsJobDetails>
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

@Serializable
data class SubscriptionsJobDetails(
    val id: Long,
    @SerialName("subscription_no")
    val subscriptionNo: String,
    val status: Int,
    @SerialName("is_current")
    val isCurrent: Boolean? = false,
    val isMarkedReadyToPickup: Boolean? = false,
    @SerialName("delivery_time")
    @Serializable(with = DateTimeSerializer::class)
    val deliveryAt: DateTime? = null
)
