package com.mazaj.seller.ui.splash.viewModel

import android.app.Application
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.extensions.isPresent
import com.mazaj.seller.repository.preferences.AppPreferences
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.shared.TargetActivity
import kotlin.time.seconds
import kotlinx.coroutines.delay

class SplashViewModel(application: Application) : BaseViewModel(application) {
    val navigateTo: SingleLiveEvent<TargetActivity> = SingleLiveEvent()

    init { onSplashStarted() }

    private fun onSplashStarted() {
        launchViewModelScope {
            repository.appPreferences.networkAvailable = false
            delay(2.seconds)
            navigateTo.value = if (AppPreferences.token.isPresent()) TargetActivity.HOME else TargetActivity.INTRO
        }
    }
}
