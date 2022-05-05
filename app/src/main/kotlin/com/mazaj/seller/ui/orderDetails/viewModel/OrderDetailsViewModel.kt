package com.mazaj.seller.ui.orderDetails.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.repository.networking.models.OrderDetailResponse
import com.mazaj.seller.repository.networking.models.SubscriptionsDetailsResponse
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.NEW_ACCEPTANCE_STATUS

class OrderDetailsViewModel(application: Application) : BaseViewModel(application) {
    val orderDetailsLiveData = MutableLiveData<OrderDetailResponse>()
    val subscriptionOrderDetailsLiveData = MutableLiveData<SubscriptionsDetailsResponse>()
    val onOrderAccepted = SingleLiveEvent<Void>()

    fun getOrderDetail(id: Long) = launchViewModelScope {
        isFormLoading.value = true
        orderDetailsLiveData.value = repository.getOrderDetails(id).body()
        isFormLoading.value = false
    }

    fun getSubscriptionDetails(subscriptionId: Long) = launchViewModelScope {
        isFormLoading.value = true
        subscriptionOrderDetailsLiveData.value = repository.getSubscriptionDetails(subscriptionId).body()
        isFormLoading.value = false
    }

    fun onActionButtonClicked() = launchViewModelScope {
        isFormLoading.value = true
        if (orderDetailsLiveData.value?.acceptanceStatus == NEW_ACCEPTANCE_STATUS && orderDetailsLiveData.value?.type == 1) {
            repository.acceptOrder(orderDetailsLiveData.value?.id!!)
        } else if (subscriptionOrderDetailsLiveData.value?.isAccepted != true && subscriptionOrderDetailsLiveData.value?.type == 2) {
            repository.acceptSubscription(subscriptionOrderDetailsLiveData.value?.id!!)
        } else {
            if (orderDetailsLiveData.value?.type == 1) {
                repository.setOrderAsReadyForPick(orderDetailsLiveData.value?.id!!)
            } else {
                repository.setSubscriptionReadyForPickup(subscriptionOrderDetailsLiveData.value?.id!!)
            }
        }
        onOrderAccepted.call()
        isFormLoading.value = false
    }

    fun declineOrder(reason: String) = launchViewModelScope {
        isFormLoading.value = true
        if (orderDetailsLiveData.value?.type == 1) {
            repository.declineOrder(orderDetailsLiveData.value?.id!!, reason)
        } else {
            repository.declineSubscription(subscriptionOrderDetailsLiveData.value?.id!!, reason)
        }
        onOrderAccepted.call()
        isFormLoading.value = false
    }
}
