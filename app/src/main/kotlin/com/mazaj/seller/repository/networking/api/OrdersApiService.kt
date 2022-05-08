package com.mazaj.seller.repository.networking.api

import com.mazaj.seller.repository.networking.models.*
import retrofit2.Response
import retrofit2.http.*

interface OrdersApiService {
    @GET("order-overviews")
    suspend fun getOrders(@Query("status_group") status: String): Response<OrderResponse>

    @GET("orders/counts")
    suspend fun getOrdersOverviewCounts(): Response<OrderOverviewCountsResponse>

    @GET("orders/{id}")
    suspend fun getOrderDetails(@Path("id") id: Long): Response<OrderDetailResponse>

    @GET("subscriptions/{id}")
    suspend fun getSubscriptionDetails(@Path("id") id: Long): Response<SubscriptionsDetailsResponse>

    @GET("orders/list")
    suspend fun getOrdersList(@Query("status") status: Int): Response<OrderResponse>

    @GET("subscriptions")
    suspend fun getSubscriptionsList(@Query("status_group") status: String = "ALL"): Response<MySubscriptionsResponse>

    @POST("orders/{id}/accept")
    suspend fun acceptOrder(@Path("id") id: Long): Response<OrderReplyResponse>

    @POST("subscriptions/{id}/accept")
    suspend fun acceptSubscriptions(@Path("id") id: Long): Response<OrderReplyResponse>

    @POST("subscriptions/{id}/decline")
    suspend fun declineSubscriptions(@Path("id") id: Long, @Body body: DeclineOrderBody): Response<OrderReplyResponse>

    @POST("orders/{id}/decline")
    suspend fun declineOrder(@Path("id") id: Long, @Body body: DeclineOrderBody): Response<OrderReplyResponse>

    @POST("orders/{id}/ready-for-pickup")
    suspend fun setOrderAsReadyForPick(@Path("id") id: Long): Response<OrderReplyResponse>
}
