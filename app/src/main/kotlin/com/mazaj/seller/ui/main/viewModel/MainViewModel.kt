package com.mazaj.seller.ui.main.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.repository.networking.models.Order
import com.mazaj.seller.repository.networking.models.OrdersOverviewCounts
import com.mazaj.seller.repository.repository

class MainViewModel(application: Application) : BaseViewModel(application) {
    val onLogoutSucceeded = SingleLiveEvent<Void>()
    val newOrdersLiveData = MutableLiveData<List<Order>>()
    val acceptedOrdersLiveData = MutableLiveData<List<Order>>()
    val readyOrdersLiveData = MutableLiveData<List<Order>>()
    val overviewCountsLiveData = MutableLiveData<List<OrdersOverviewCounts>?>()

    fun getOrders() = launchViewModelScope {
        isScreenLoading.value = true
        readyOrdersLiveData.value = repository.getOrders(READY).body()?.data
        acceptedOrdersLiveData.value = repository.getOrders(ACCEPTED).body()?.data
        newOrdersLiveData.value = repository.getOrders(NEW).body()?.data
        overviewCountsLiveData.value = repository.getOrdersOverviewCounts().body()
        isScreenLoading.value = false
    }

    fun onLogoutClicked() {
        repository.appPreferences.token = null
        onLogoutSucceeded.call()
    }

    companion object {
        const val NEW = "NEW"
        const val NEW_STATUS = 60
        const val ACCEPTED = "ACCEPTED"
        const val ACCEPTED_STATUS = 65
        const val READY = "READY"
        const val READY_STATUS = 70
        const val NEW_ACCEPTANCE_STATUS = 2
        const val ACCEPTED_ACCEPTANCE_STATUS = 3
    }
}
