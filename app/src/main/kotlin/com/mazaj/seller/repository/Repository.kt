package com.mazaj.seller.repository

import android.content.Context
import com.mazaj.seller.repository.networking.RetrofitClient
import com.mazaj.seller.repository.preferences.AppPreferences

lateinit var repository: Repository

class Repository(context: Context) : AuthenticationRepository {
    override var authenticationApiService = RetrofitClient.authenticationApiService

    override val appPreferences = AppPreferences.apply { setup(context) }
}
