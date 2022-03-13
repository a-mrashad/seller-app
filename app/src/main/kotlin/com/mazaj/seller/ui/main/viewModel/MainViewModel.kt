package com.mazaj.seller.ui.main.viewModel

import android.app.Application
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.repository.repository

class MainViewModel(application: Application) : BaseViewModel(application) {
    val onLogoutSucceeded = SingleLiveEvent<Void>()

    fun onLogoutClicked() {
        repository.appPreferences.token = null
        onLogoutSucceeded.call()
    }
}
