package com.mazaj.seller.repository

import com.mazaj.seller.repository.networking.api.AuthenticationApiService
import com.mazaj.seller.repository.networking.models.ForgetPasswordBody
import com.mazaj.seller.repository.networking.models.LoginBody
import com.mazaj.seller.repository.networking.models.ResetPasswordBody
import com.mazaj.seller.repository.preferences.AppPreferences

interface AuthenticationRepository {
    var authenticationApiService: AuthenticationApiService
    val appPreferences: AppPreferences

    suspend fun authenticateUser(username: String, password: String, deviceId: String) = authenticationApiService.login(
        LoginBody(username, password, deviceId)
    ).body()?.apply {
        appPreferences.token = accessToken
        appPreferences.fcmToken = refreshToken
    }

    suspend fun forgetPassword(email: String) = authenticationApiService.forgetPassword(ForgetPasswordBody(email))

    suspend fun resetPassword(body: ResetPasswordBody) = authenticationApiService.resetPassword(body)

    fun alreadyAuthenticated() = appPreferences.let { listOf(it.token, it.fcmToken).all { value -> value?.isNotEmpty()!! } }
}
