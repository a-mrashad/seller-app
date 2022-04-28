package com.mazaj.seller.repository

import com.mazaj.seller.repository.networking.api.OrdersApiService
import com.mazaj.seller.repository.networking.models.DeclineOrderBody
import com.mazaj.seller.repository.networking.models.OrderOverviewCountsResponse
import com.mazaj.seller.repository.preferences.AppPreferences
import retrofit2.Response

interface OrdersRepository {
    var orderApiService: OrdersApiService
    val appPreferences: AppPreferences

    suspend fun getOrders(status: String) = orderApiService.getOrders(status)

    suspend fun getOrdersOverviewCounts(): Response<OrderOverviewCountsResponse> = orderApiService.getOrdersOverviewCounts()

    suspend fun getOrderDetails(orderId: Long) = orderApiService.getOrderDetails(orderId)

    suspend fun getSubscriptionDetails(subId: Long) = orderApiService.getSubscriptionDetails(subId)

    suspend fun getOrdersList(status: Int) = orderApiService.getOrdersList(status)

    suspend fun acceptOrder(orderId: Long) = orderApiService.acceptOrder(orderId)

    suspend fun declineOrder(orderId: Long) = orderApiService.declineOrder(orderId, DeclineOrderBody("order_reason"))

    suspend fun acceptSubscription(orderId: Long) = orderApiService.acceptSubscriptions(orderId)

    suspend fun declineSubscription(orderId: Long) = orderApiService.declineSubscriptions(orderId, DeclineOrderBody("subscription_reason"))

    suspend fun setOrderAsReadyForPick(orderId: Long) = orderApiService.setOrderAsReadyForPick(orderId)

    suspend fun setSubscriptionReadyForPickup(orderId: Long) = orderApiService.setSubscriptionReadyForPickup(orderId)
}
