package com.mazaj.seller.ui.orderDetails.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.repository.networking.models.OrderDetailResponse
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.NEW_ACCEPTANCE_STATUS

class OrderDetailsViewModel(application: Application) : BaseViewModel(application) {
    val orderDetailsLiveData = MutableLiveData<OrderDetailResponse>()
    val onOrderAccepted = SingleLiveEvent<Void>()

    fun getOrderDetail(id: String) = launchViewModelScope {
        isFormLoading.value = true
        orderDetailsLiveData.value = repository.getOrderDetails(id).body()
        isFormLoading.value = false
    }

    fun onActionButtonClicked() = launchViewModelScope {
        isFormLoading.value = true
        if (orderDetailsLiveData.value?.acceptanceStatus == NEW_ACCEPTANCE_STATUS) {
            repository.acceptOrder(orderDetailsLiveData.value?.id!!)
        } else {
            repository.setOrderAsReadyForPick(orderDetailsLiveData.value?.id!!)
        }
        onOrderAccepted.call()
        isFormLoading.value = false
    }

    fun declineOrder() = launchViewModelScope {
        isFormLoading.value = true
        repository.declineOrder(orderDetailsLiveData.value?.id!!)
        onOrderAccepted.call()
        isFormLoading.value = false
    }
}
