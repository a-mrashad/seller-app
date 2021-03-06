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
    @SerialName("order_number")
    val orderNumber: String?,
    @SerialName("delivery_at")
    @Serializable(with = DateTimeSerializer::class)
    val deliveryAt: DateTime? = null,
    @SerialName("time_to_auto_decline")
    @Serializable(with = DateTimeSerializer::class)
    val timeToAutoDecline: DateTime? = null,
    @SerialName("pickup_at")
    val pickupAt: String,
    @SerialName("items_count")
    val itemsCount: Int = 0,
    @SerialName("total_price")
    val totalPrice: Double? = null,
    @SerialName("vat_details")
    val vatDetails: String? = null,
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
    @SerialName("price_option")
    val priceOptions: OrderPriceOptions? = null,
    val addOns: MutableList<OrderAddOns>? = null,
    val options: MutableList<OrderOptions>? = null,
    val variants: MutableList<OrderVariants>? = null
)

@Serializable
data class SubscriptionsJobDetails(
    val id: Long,
    @SerialName("subscription_no")
    val subscriptionNo: String,
    @SerialName("is_current")
    val isCurrent: Boolean? = false,
    @SerialName("is_marked_ready_to_pickup")
    val isMarkedReadyToPickup: Boolean? = true,
    @SerialName("delivery_time")
    @Serializable(with = DateTimeSerializer::class)
    val deliveryAt: DateTime? = null,
    @SerialName("is_delivered")
    val isDelivered: Boolean = false,
    @SerialName("time_to_auto_decline")
    @Serializable(with = DateTimeSerializer::class)
    val timeToAutoDecline: DateTime? = null
)
