package com.mazaj.seller.repository.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderOverviewCountsResponse(
    val data: List<OrdersOverviewCounts>,
    @SerialName("seller_info")
    val sellerInfo: SellerInfoDetails
)

@Serializable
data class OrdersOverviewCounts(
    val total: Long,
    val status: String,
    @SerialName("status_label")
    val statusLabel: String
)

@Serializable
data class SellerInfoDetails(
    @SerialName("is_opened")
    val isOpened: Boolean? = true,
    @SerialName("seller_name")
    val sellerName: String? = "",
    @SerialName("branch_name")
    val branchName: String? = ""
)
