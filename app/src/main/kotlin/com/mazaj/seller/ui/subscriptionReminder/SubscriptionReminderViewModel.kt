package com.mazaj.seller.ui.subscriptionReminder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.repository.networking.models.SubscriptionReminder
import com.mazaj.seller.repository.repository

class SubscriptionReminderViewModel(application: Application) : BaseViewModel(application) {
    val subscriptionsLiveData = MutableLiveData<List<SubscriptionReminder>>()

    init {
        getReminderDetails()
    }

    private fun getReminderDetails() = launchViewModelScope {
        isScreenLoading.value = true
        subscriptionsLiveData.value = repository.getSubscriptionReminderDetails().body()?.data ?: mutableListOf()
        isScreenLoading.value = false
    }
}
