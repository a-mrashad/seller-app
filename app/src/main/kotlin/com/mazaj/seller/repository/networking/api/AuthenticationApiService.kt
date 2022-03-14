package com.mazaj.seller.repository.networking.api

import com.mazaj.seller.repository.networking.models.LoginResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthenticationApiService {
    @POST("login")
    suspend fun login(@Query("username") username: String, @Query("password") password: String): Response<LoginResponse>
}
