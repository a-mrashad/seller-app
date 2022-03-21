package com.mazaj.seller.repository

import com.mazaj.seller.repository.networking.api.OrdersApiService
import com.mazaj.seller.repository.preferences.AppPreferences

interface OrdersRepository {
    var orderApiService: OrdersApiService
    val appPreferences: AppPreferences

    suspend fun getOrders(status: String) = orderApiService.getOrders(status)
}