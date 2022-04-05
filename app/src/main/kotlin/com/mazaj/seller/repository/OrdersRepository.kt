package com.mazaj.seller.repository

import com.mazaj.seller.repository.networking.api.OrdersApiService
import com.mazaj.seller.repository.networking.models.OrdersOverviewCounts
import com.mazaj.seller.repository.preferences.AppPreferences
import retrofit2.Response

interface OrdersRepository {
    var orderApiService: OrdersApiService
    val appPreferences: AppPreferences

    suspend fun getOrders(status: String) = orderApiService.getOrders(status)

    suspend fun getOrdersOverviewCounts(): Response<List<OrdersOverviewCounts>> = orderApiService.getOrdersOverviewCounts()

    suspend fun getOrderDetails(orderId: String) = orderApiService.getOrderDetails(orderId)

    suspend fun acceptOrder(orderId: String) = orderApiService.acceptOrder(orderId)

    suspend fun setOrderAsReadyForPick(orderId: String) = orderApiService.setOrderAsReadyForPick(orderId)
}
