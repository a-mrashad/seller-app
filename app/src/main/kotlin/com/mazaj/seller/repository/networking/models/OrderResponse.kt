package com.mazaj.seller.repository.networking.models

import com.mazaj.seller.utils.DateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Serializable
data class OrderResponse(
    val data: List<Order>
)

@Serializable
data class Order(
    val id: String,
    @SerialName("order_number")
    val orderNumber: String,
    @SerialName("pickup_at")
    @Serializable(with = DateTimeSerializer::class)
    val pickupAt: DateTime? = null,
    @SerialName("delivery_at")
    @Serializable(with = DateTimeSerializer::class)
    val deliveryAt: DateTime,
    @SerialName("items_count")
    val itemsCount: Int,
    @SerialName("payment_status")
    val paymentStatus: Int? = null,
    @SerialName("payment_status_label")
    val paymentStatusLabel: String? = null,
    @SerialName("payment_type")
    val paymentType: Int? = null,
    @SerialName("payment_type_label")
    val paymentTypeLabel: String? = null,
    val items: List<OrderItem>? = null,
    val price: OrderPrice? = null,
    @SerialName("add_ons")
    val addOns: OrderAddOns? = null,
    val options: OrderOptions? = null,
    val variants: OrderVariants? = null
)

@Serializable
data class OrderItem(
    val id: String,
    @SerialName("item_id")
    val itemId: String,
    @SerialName("price_per_item")
    val pricePerItem: String? = null,
    @SerialName("en_unit_name")
    val enUnitName: String? = null,
    @SerialName("ar_unit_name")
    val arUnitName: String? = null,
    val quantity: Int? = null,
    val total: Float? = null,
    @SerialName("additional_information")
    val additionalInformation: OrderAdditionalInformation? = null,
    @SerialName("add_ons")
    val addOns: MutableList<OrderAddOns>? = null,
    val options: MutableList<OrderOptions>? = null,
    val variants: MutableList<OrderVariants>? = null,
    val price: OrderPrice? = null
)

@Serializable
data class OrderAdditionalInformation(
    val id: String,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    @SerialName("en_brief")
    val enBrief: String? = null,
    @SerialName("ar_brief")
    val arBrief: String? = null,
    @SerialName("calories_note_en")
    val caloriesNoteEn: String? = null,
    @SerialName("calories_note_ar")
    val caloriesNoteAr: String? = null,
    @SerialName("preparation_time")
    val preparationTime: Long? = null,
    @SerialName("preparation_time_label")
    val preparationTimeLabel: String? = null,
    @SerialName("price_type")
    val priceType: Int? = null,
    @SerialName("price_type_label")
    val priceTypeLabel: String? = null,
    @SerialName("section_id")
    val sectionId: String? = null,
    @SerialName("seller_id")
    val sellerId: String? = null,
    val status: Int? = null,
    @SerialName("status_label")
    val statusLabel: String? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null
)

@Serializable
data class OrderPrice(
    val id: String,
    @SerialName("unit_name_en")
    val unitNameEn: String? = null,
    @SerialName("unit_name_ar")
    val unitNameAr: String? = null,
    val price: String? = null,
    @SerialName("of_serves")
    val ofServes: String? = null,
    @SerialName("allow_to_choose")
    val allowToChoose: String? = null,
    @SerialName("unit_id")
    val unitId: String? = null,
    @SerialName("item_id")
    val itemId: String? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null
)

@Serializable
data class OrderAddOns(
    val id: String? = null,
    val price: String,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    @SerialName("item_id")
    val itemId: String? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null
)

@Serializable
data class OrderOptions(
    val price: String? = null,
    val id: String? = null,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    @SerialName("item_option_id")
    val itemOptionId: String? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null,
    @SerialName("item_option")
    val itemOption: OrderItemOption? = null
)

@Serializable
data class OrderItemOption(
    val id: String,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    @SerialName("item_id")
    val itemId: String? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null
)

@Serializable
data class OrderVariants(
    val id: String? = null,
    val quantity: String? = null,
    @SerialName("en_name")
    val name: String? = null
)

@Serializable
data class AcceptOrderResponse(
    val message: String? = null
)
