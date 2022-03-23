package com.mazaj.seller.repository.networking.api

import com.mazaj.seller.repository.networking.models.LoginBody
import com.mazaj.seller.repository.networking.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApiService {
    @POST("login")
    suspend fun login(@Body body: LoginBody): Response<LoginResponse>
}
