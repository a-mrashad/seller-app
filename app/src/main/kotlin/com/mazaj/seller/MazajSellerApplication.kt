package com.mazaj.seller

import android.app.Application
import com.mazaj.seller.repository.Repository
import com.mazaj.seller.repository.repository
import com.mazaj.seller.utils.NotificationHelperUtils

class MazajSellerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelperUtils(this)
        repository = Repository(this)
    }
}
