package com.mazaj.seller.repository

import com.mazaj.seller.repository.networking.api.AuthenticationApiService
import com.mazaj.seller.repository.preferences.AppPreferences

interface AuthenticationRepository {
    var authenticationApiService: AuthenticationApiService
    val appPreferences: AppPreferences

    suspend fun authenticateUser(username: String, password: String) = authenticationApiService.login(username, password).apply {
        appPreferences.token = headers()["access_token"]!!
        appPreferences.refresh_token = headers()["refresh_token"]!!
    }

    fun alreadyAuthenticated() = appPreferences.let { listOf(it.token, it.refresh_token).all { value -> value?.isNotEmpty()!! } }
}
