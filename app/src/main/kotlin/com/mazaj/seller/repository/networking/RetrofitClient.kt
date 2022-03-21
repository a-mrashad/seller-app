package com.mazaj.seller.repository.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mazaj.seller.BuildConfig
import com.mazaj.seller.Constants.PATH
import com.mazaj.seller.common.ResponseInterceptor
import com.mazaj.seller.repository.networking.api.AuthenticationApiService
import com.mazaj.seller.repository.networking.api.OrdersApiService
import com.mazaj.seller.repository.networking.models.ErrorBody
import java.util.concurrent.TimeUnit
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 5L
private const val READ_TIMEOUT = 3L

object RetrofitClient {
    private val responseInterceptor by lazy { ResponseInterceptor(ResponseInterceptor.Logger.DEFAULT, parsingErrors) }
    private val credentialsInterceptor by lazy { CredentialsInterceptor() }
    private val localeInterceptor by lazy { LocaleInterceptor() }
    private val hostApi = "${BuildConfig.BASE_URL}$PATH"

    private val parsingErrors: (String?) -> (Any) = { error: String? ->
        try {
            val json = jsonDefaultConfig()
            json.parse(ErrorBody.serializer(), error!!)
        } catch (serializationException: SerializationException) {
            ErrorBody()
        }
    }

    private fun jsonDefaultConfig() = Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true, isLenient = true))

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(localeInterceptor)
            .addInterceptor(responseInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    }

    private fun retrofit(withAuthenticationHeaders: Boolean = true): Retrofit {
        if (withAuthenticationHeaders) okHttpClient.addInterceptor(credentialsInterceptor)
        val contentType = jsonDefaultConfig().asConverterFactory("application/json".toMediaType())

        return Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(hostApi)
            .addConverterFactory(contentType)
            .build()
    }

    val authenticationApiService by lazy { retrofit(withAuthenticationHeaders = false).create(AuthenticationApiService::class.java) }
    val ordersApiService by lazy { retrofit(withAuthenticationHeaders = true).create(OrdersApiService::class.java) }
}
