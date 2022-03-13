package com.mazaj.seller

import android.app.Application
import com.mazaj.seller.repository.Repository
import com.mazaj.seller.repository.repository

class MazajSellerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        repository = Repository(this)
    }
}
