package com.mazaj.seller.repository.networking.api

import com.mazaj.seller.repository.networking.models.*
import retrofit2.Response
import retrofit2.http.*

interface OrdersApiService {
    @GET("order-overviews")
    suspend fun getOrders(@Query("status_group") status: String): Response<OrderResponse>

    @GET("orders/counts")
    suspend fun getOrdersOverviewCounts(): Response<List<OrdersOverviewCounts>>

    @GET("orders/{id}")
    suspend fun getOrderDetails(@Path("id") id: String): Response<OrderDetailResponse>

    @GET("orders/list")
    suspend fun getOrdersList(@Query("status") status: Int): Response<OrderResponse>

    @POST("orders/{id}/accept")
    suspend fun acceptOrder(@Path("id") id: String): Response<OrderReplyResponse>

    @POST("orders/{id}/decline")
    suspend fun declineOrder(@Path("id") id: String, @Body body: DeclineOrderBody): Response<OrderReplyResponse>

    @POST("orders/{id}/ready-for-pickup")
    suspend fun setOrderAsReadyForPick(@Path("id") id: String): Response<OrderReplyResponse>
}
