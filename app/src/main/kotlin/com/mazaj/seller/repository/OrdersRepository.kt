package com.mazaj.seller.repository

import com.mazaj.seller.repository.networking.api.OrdersApiService
import com.mazaj.seller.repository.preferences.AppPreferences

interface OrdersRepository {
    var orderApiService: OrdersApiService
    val appPreferences: AppPreferences

    suspend fun getOrders(status: String) = orderApiService.getOrders(status)

    suspend fun getOrderDetail(orderId: String) = orderApiService.getOrderDetails(orderId)

    suspend fun acceptOrder(orderId: String) = orderApiService.acceptOrder(orderId)

    suspend fun setOrderAsReadyForPick(orderId: String) = orderApiService.setOrderAsReadyForPick(orderId)
}
