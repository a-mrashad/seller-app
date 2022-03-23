package com.mazaj.seller.repository.networking

import java.io.IOException
import java.util.*
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class LocaleInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .url(addQueryParameters(request.url))
            .method(request.method, request.body)
            .addHeader(ACCEPT, "application/json")
            .build()
        return chain.proceed(newRequest)
    }

    private fun addQueryParameters(url: HttpUrl) = url.newBuilder().addQueryParameter(LOCALE, Locale.getDefault().language).build()

    companion object {
        private const val LOCALE = "lang"
        private const val ACCEPT = "Accept"
    }
}
