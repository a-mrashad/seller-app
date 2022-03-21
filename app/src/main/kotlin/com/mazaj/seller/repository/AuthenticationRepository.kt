package com.mazaj.seller.repository

import com.mazaj.seller.repository.networking.api.AuthenticationApiService
import com.mazaj.seller.repository.preferences.AppPreferences

interface AuthenticationRepository {
    var authenticationApiService: AuthenticationApiService
    val appPreferences: AppPreferences

    suspend fun authenticateUser(username: String, password: String) = authenticationApiService.login(username, password).body()?.apply {
        appPreferences.token = accessToken
        appPreferences.refreshToken = refreshToken
    }

    fun alreadyAuthenticated() = appPreferences.let { listOf(it.token, it.refreshToken).all { value -> value?.isNotEmpty()!! } }
}
