package com.mazaj.seller.ui.ordersList.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.shared.pagination.ListItem
import com.mazaj.seller.ui.shared.pagination.PaginationViewModel

class OrdersListViewModel(application: Application) : BaseViewModel(application), PaginationViewModel {
    override val itemList = MutableLiveData<List<ListItem>>()
    override var lastPageSize: Int? = null
    override var page: Int = 1
    override val isListLoading = MutableLiveData<Boolean>()
    val countLiveData = MutableLiveData<Int>()

    val statusLiveData = MutableLiveData<Int>()

    fun onStatusReceived(status: Int) {
        statusLiveData.value = status
    }

    override suspend fun fetchItems(page: Int, perPage: Int): List<ListItem> {
        val orders = mutableListOf<ListItem>()
        val response = repository.getOrdersList(statusLiveData.value!!, page).body()
        countLiveData.value = response?.meta?.total ?: 0
        response?.data?.forEach { orders.add(it) }
        return orders
    }
}
