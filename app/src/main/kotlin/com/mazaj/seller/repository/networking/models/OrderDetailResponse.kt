package com.mazaj.seller.repository.networking.models

import com.mazaj.seller.utils.DateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Serializable
data class OrderDetailResponse(
    val id: String,
    val status: Int,
    @SerialName("status_label")
    val statusLabel: String? = null,
    @SerialName("acceptance_status")
    val acceptanceStatus: Int? = null,
    @SerialName("acceptance_status_label")
    val acceptanceStatusLabel: String? = null,
    val logs: List<OrderLogs>? = null,
    // val compensations: List<String>? = null,
    // val refunds: List<String>? = null,
    @SerialName("sub_total_without_delivery")
    val subTotalWithoutDelivery: String? = null,
    @SerialName("total_price")
    val totalPrice: String? = null,
    @SerialName("order_number")
    val orderNumber: String? = null,
    @SerialName("delivery_earn")
    val deliveryEarn: Int? = null,
    @SerialName("delivery_fees_after_discount")
    val deliveryFeesAfterDiscount: String? = null,
    @SerialName("delivery_vat")
    val deliveryVat: Int? = null,
    @SerialName("delivery_vat_value")
    val deliveryVatValue: String? = null,
    @SerialName("total_items_vat")
    val totalItemsVat: Int? = null,
    @SerialName("total_items_vat_value")
    val totalItemsVatValue: String? = null,
    @SerialName("total_vat")
    val totalVat: String? = null,
    val tracking: Tracking? = null,
    @SerialName("can_refund")
    val canRefund: Boolean? = null,
    @SerialName("can_compensate")
    val canCompensate: Boolean? = null,
    @SerialName("pickup_at")
    @Serializable(with = DateTimeSerializer::class)
    val pickupAt: DateTime,
    @SerialName("delivery_at")
    @Serializable(with = DateTimeSerializer::class)
    val deliveryAt: DateTime,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime,
    @SerialName("updated_at")
    @Serializable(with = DateTimeSerializer::class)
    val updatedAt: DateTime,
    @SerialName("customer_id")
    val customerId: Int? = null,
    val customer: Customer? = null,
    val delivery: OrderDelivery? = null,
    val branch: Branch? = null,
    val coupon: String? = null,
    val items: MutableList<OrderItem>? = null,
    val type: Int? = null,
    @SerialName("type_label")
    val typeLabel: String? = null,
    @SerialName("payment_status")
    val paymentStatus: Int? = null,
    @SerialName("payment_status_label")
    val paymentStatusLabel: String? = null,
    @SerialName("payment_type")
    val paymentType: Int? = null,
    @SerialName("payment_type_label")
    val paymentTypeLabel: String? = null,
    @SerialName("can_update_delivery_time")
    val canUpdateDeliveryTime: Boolean? = null,
    @SerialName("coupon_id")
    val couponId: String? = null
)

@Serializable
data class OrderDelivery(
    val id: String,
    val address: String,
    val longitude: Double,
    val latitude: Double
)

@Serializable
data class OrderLogs(
    val id: String,
    val status: Int,
    @SerialName("status_label")
    val statusLabel: String? = null,
    @SerialName("acceptance_status")
    val acceptanceStatus: Int? = null,
    @SerialName("acceptance_status_label")
    val acceptanceStatusLabel: String? = null,
    @SerialName("owner_name")
    val ownerName: String? = null,
    @SerialName("owner_label")
    val ownerLabel: String? = null,
    val reason: String? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime
)

@Serializable
data class Tracking(
    val step: Int? = null,
    @SerialName("payment_type")
    val statusText: String? = null,
    @SerialName("status_details")
    val statusDetails: List<String>? = null
)

@Serializable
data class Customer(
    val id: Int? = null,
    val name: String? = null,
    val mobile: Int? = null,
    @SerialName("note_to_driver")
    val noteToDriver: String? = null
)

@Serializable
data class Branch(
    val id: String? = null,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    @SerialName("city_id")
    val cityId: Int? = null,
    val city: City? = null,
    @SerialName("seller_id")
    val sellerId: Int? = null,
    val seller: Seller? = null,
    val email: String? = null
)

@Serializable
data class City(
    val id: Int? = null,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    val status: Int? = null,
    @SerialName("status_label")
    val statusLabel: String? = null,
    val geom: List<List<Double>>? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime,
    @SerialName("country_id")
    val countryId: Int? = null
)

@Serializable
data class Seller(
    val id: Int? = null,
    @SerialName("en_name")
    val enName: String? = null,
    @SerialName("ar_name")
    val arName: String? = null,
    @SerialName("about_en")
    val aboutEn: String? = null,
    @SerialName("about_ar")
    val aboutAr: String? = null,
    @SerialName("commercial_name_en")
    val commercialNameEn: String? = null,
    @SerialName("commercial_name_ar")
    val commercialNameAr: String? = null,
    @SerialName("city_id")
    val cityId: String? = null,
    val website: String? = null,
    @SerialName("legal_email")
    val legalEmail: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    @SerialName("head_office_address")
    val headOfficeAddress: String? = null,
    val email: String? = null,
    @SerialName("beneficiary_name")
    val beneficiaryName: String? = null,
    @SerialName("bank_id")
    val bankId: String? = null,
    @SerialName("time_of_money_transfer")
    val timeOfMoneyTransfer: Int? = null,
    val iban: String? = null,
    @SerialName("vat_certificate")
    val vatCertificate: String? = null,
    @SerialName("mazaj_sales_percentage")
    val mazajSalesPercentage: String? = null,
    val status: Int? = null,
    @SerialName("status_label")
    val statusLabel: String? = null,
    val type: Int? = null,
    @SerialName("type_label")
    val typeLabel: String? = null,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: DateTime
)
