package com.mazaj.seller.ui.main.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.repository.networking.models.Order
import com.mazaj.seller.repository.networking.models.OrdersOverviewCounts
import com.mazaj.seller.repository.networking.models.SellerInfoDetails
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.shared.pagination.ListItem
import com.mazaj.seller.ui.shared.pagination.PaginationViewModel

class MainViewModel(application: Application) : BaseViewModel(application), PaginationViewModel {
    override val itemList = MutableLiveData<List<ListItem>>()
    override var lastPageSize: Int? = null
    override var page: Int = 1
    override val isListLoading = MutableLiveData<Boolean>()

    val onLogoutSucceeded = SingleLiveEvent<Void>()
    val newOrdersLiveData = MutableLiveData<List<Order>>()
    val acceptedOrdersLiveData = MutableLiveData<List<Order>>()
    val readyOrdersLiveData = MutableLiveData<List<Order>>()
    val overviewCountsLiveData = MutableLiveData<List<OrdersOverviewCounts>?>()
    val branchStatusMutableLiveData = MutableLiveData<SellerInfoDetails>()

    override suspend fun fetchItems(page: Int, perPage: Int): List<ListItem> {
        val subscriptions = mutableListOf<ListItem>()
        val responseData = repository.getSubscriptions(page).body()?.data
        responseData?.forEach { subscriptions.add(it) }
        return subscriptions
    }

    fun getOrders() = launchViewModelScope {
        isScreenLoading.value = true
        readyOrdersLiveData.value = repository.getOrders(READY).body()?.data
        acceptedOrdersLiveData.value = repository.getOrders(ACCEPTED).body()?.data
        newOrdersLiveData.value = repository.getOrders(NEW).body()?.data
        repository.getOrdersOverviewCounts().body()?.apply {
            overviewCountsLiveData.value = data
            branchStatusMutableLiveData.value = sellerInfo
        }
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
        const val NEW_SUBSCRIPTION_STATUS = 1
        const val ACCEPTED_SUBSCRIPTION_STATUS = 15
        const val ACCEPTED_ACCEPTANCE_STATUS = 3
    }
}
