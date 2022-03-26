package com.mazaj.seller.ui.orderDetails.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.repository.networking.models.OrderDetailResponse
import com.mazaj.seller.repository.repository

class OrderDetailsViewModel(application: Application) : BaseViewModel(application) {
    val orderDetailLiveData = MutableLiveData<OrderDetailResponse>()

    fun getOrderDetail(id: String) = launchViewModelScope {
        isFormLoading.value = true
        orderDetailLiveData.value = repository.orderApiService.getOrderDetails(id).body()
        isFormLoading.value = false
    }

    fun acceptOrder() = launchViewModelScope {
        isFormLoading.value = true
        // val response = repository.orderApiService.acceptOrder(orderDetailLiveData.value?.id!!)
        // TODO handle after seller accept
        isFormLoading.value = false
    }
}
