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

    suspend fun getOrdersList(status: Int, page: Int) = orderApiService.getOrdersList(status, page)

    suspend fun getSubscriptions(page: Int) = orderApiService.getSubscriptionsList(page = page)

    suspend fun acceptOrder(orderId: Long) = orderApiService.acceptOrder(orderId)

    suspend fun declineOrder(orderId: Long, reason: String) = orderApiService.declineOrder(orderId, DeclineOrderBody(reason))

    suspend fun acceptSubscription(orderId: Long) = orderApiService.acceptSubscriptions(orderId)

    suspend fun declineSubscription(orderId: Long, reason: String) = orderApiService.declineSubscriptions(orderId, DeclineOrderBody(reason))

    suspend fun setOrderAsReadyForPick(orderId: Long) = orderApiService.setOrderAsReadyForPick(orderId)
}
