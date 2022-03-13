package com.mazaj.seller.ui.shared.network

import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.repository.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface NetworkStatusViewModel {
    val coroutineScope: CoroutineScope

    val networkAvailability: MutableLiveData<Boolean>
    val hideInternetStatus: SingleLiveEvent<Void>

    fun onConnectivityChanged(currentlyConnected: Boolean = repository.appPreferences.networkAvailable) {
        val wasConnectedBefore = repository.appPreferences.networkAvailable
        val showNetStatus = !currentlyConnected || wasConnectedBefore != currentlyConnected

        repository.appPreferences.networkAvailable = currentlyConnected

        if (showNetStatus) {
            networkAvailability.postValue(currentlyConnected)
            if (currentlyConnected) {
                coroutineScope.launch {
                    delay(com.mazaj.seller.Constants.RECONNECTED_STATUS_DURATION)
                    hideInternetStatus.call()
                }
            }
        }
    }

    fun onViewCreated() {
        if (!repository.appPreferences.networkAvailable) {
            networkAvailability.value = false
        }
    }

    fun onViewAppeared() = if (!repository.appPreferences.networkAvailable) networkAvailability.value = false else hideInternetStatus.call()
}
