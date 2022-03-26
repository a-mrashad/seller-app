package com.mazaj.seller.repository.networking.api

import com.mazaj.seller.repository.networking.models.AcceptOrderResponse
import com.mazaj.seller.repository.networking.models.OrderDetailResponse
import com.mazaj.seller.repository.networking.models.OrderResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrdersApiService {
    @GET("order-overviews")
    suspend fun getOrders(@Query("status_group") status: String): Response<OrderResponse>

    @GET("orders/{id}")
    suspend fun getOrderDetails(@Path("id") id: String): Response<OrderDetailResponse>

    @POST("orders/{id}/accept")
    suspend fun acceptOrder(@Path("id") id: String): Response<AcceptOrderResponse>

    @POST("orders/{id}/ready-for-pickup")
    suspend fun setOrderAsReadyForPick(@Path("id") id: String): Response<AcceptOrderResponse>
}
