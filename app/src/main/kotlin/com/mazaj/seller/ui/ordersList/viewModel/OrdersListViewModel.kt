package com.mazaj.seller.ui.ordersList.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.repository.networking.models.Order
import com.mazaj.seller.repository.repository

class OrdersListViewModel(application: Application) : BaseViewModel(application) {
    val ordersLiveData = MutableLiveData<List<Order>>()

    fun onStatusReceived(status: String) = launchViewModelScope {
        isScreenLoading.value = true
        ordersLiveData.value = repository.getOrders(status).body()?.data
        isScreenLoading.value = false
    }
}
