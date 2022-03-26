package com.mazaj.seller.repository.networking

import com.mazaj.seller.repository.repository
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

class CredentialsInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = repository.appPreferences.let {
            request.newBuilder()
                .url(request.url)
                .method(request.method, request.body)
                .addHeader(ACCESS_TOKEN, "Bearer ${it.token!!}")
                .build()
        }
        return chain.proceed(newRequest)
    }

    companion object {
        private const val ACCESS_TOKEN = "Authorization"
        // private const val REFRESH_TOKEN = "refresh_token"
    }
}
