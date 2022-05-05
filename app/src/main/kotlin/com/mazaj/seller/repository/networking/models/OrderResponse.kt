package com.mazaj.seller.repository.networking.models

import com.mazaj.seller.ui.shared.pagination.ListItem
import com.mazaj.seller.utils.DateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Serializable
data class OrderResponse(
    val data: List<Order>,
    val meta: MetaData
)

@Serializable
data class Order(
    val id: Long,
    @SerialName("order_number")
    val orderNumber: String,
    @SerialName("order_id")
    val orderId: Long? = null,
    val type: Int = 0,
    @SerialName("type_label")
    val typeLabel: String? = null,
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
    val variants: OrderVariants? = null,
    val deliveryJobId: String? = null,
    val status: Int? = null,
    @SerialName("time_to_auto_decline")
    @Serializable(with = DateTimeSerializer::class)
    val timeToAutoDecline: DateTime? = null,
    val dateString: String? = null
) : ListItem(), java.io.Serializable

@Serializable
data class OrderItem(
    val id: Long,
    @SerialName("item_id")
    val itemId: Long,
    @SerialName("price_per_item")
    val pricePerItem: Double? = null,
    @SerialName("en_unit_name")
    val enUnitName: String? = null,
    val note: String? = null,
    @SerialName("ar_unit_name")
    val arUnitName: String? = null,
    val quantity: Long? = null,
    val total: Float? = null,
    @SerialName("additional_information")
    val additionalInformation: OrderAdditionalInformation? = null,
    @SerialName("add_ons")
    val addOns: MutableList<OrderAddOns>? = null,
    val options: MutableList<OrderOptions>? = null,
    val variants: MutableList<OrderVariants>? = null,
    val price: OrderPrice? = null
) : java.io.Serializable

@Serializable
data class OrderAdditionalInformation(
    val id: Long,
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
    val sectionId: Long? = null,
    @SerialName("seller_id")
    val sellerId: Long? = null,
    val status: Int? = null,
    @SerialName("status_label")
    val statusLabel: String? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null
)

@Serializable
data class OrderPrice(
    val id: Long,
    @SerialName("unit_name_en")
    val unitNameEn: String? = null,
    @SerialName("unit_name_ar")
    val unitNameAr: String? = null,
    val price: Double? = null,
    @SerialName("of_serves")
    val ofServes: Long? = null,
    @SerialName("allow_to_choose")
    val allowToChoose: Long? = null,
    @SerialName("unit_id")
    val unitId: Long? = null,
    @SerialName("item_id")
    val itemId: Long? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null
) : java.io.Serializable

@Serializable
data class OrderAddOns(
    val id: Long? = null,
    val name: String? = null,
    val price: Double? = null,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    @SerialName("item_id")
    val itemId: Long? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null
) : java.io.Serializable

@Serializable
data class OrderOptions(
    val price: Double? = null,
    val id: Long? = null,
    val name: String? = null,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    @SerialName("item_option_id")
    val itemOptionId: Long? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null,
    @SerialName("item_option")
    val itemOption: OrderItemOption? = null
) : java.io.Serializable

@Serializable
data class OrderItemOption(
    val id: Long,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    @SerialName("item_id")
    val itemId: Long? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime? = null
) : java.io.Serializable

@Serializable
data class OrderVariants(
    val id: Long? = null,
    val details: OrderVariantDetails? = null,
    val quantity: Long? = null,
    @SerialName("en_name")
    val name: String? = null
) : java.io.Serializable

@Serializable
data class OrderVariantDetails(
    val id: Long,
    val quantity: Long? = null,
    val name: String? = null
) : java.io.Serializable

@Serializable
data class OrderReplyResponse(
    val message: String? = null
)

@Serializable
data class DeclineOrderBody(
    val reason: String
)

@Serializable
data class MetaData(
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("last_page")
    val lastPage: Int
)
