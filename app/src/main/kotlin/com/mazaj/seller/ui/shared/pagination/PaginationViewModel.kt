package com.mazaj.seller.ui.shared.pagination

import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.Constants.PER_PAGE
import com.mazaj.seller.extensions.orTrue
import com.mazaj.seller.extensions.removeFirstIf
import com.mazaj.seller.ui.shared.network.NetworkViewModel

interface PaginationViewModel : NetworkViewModel {
    val itemList: MutableLiveData<List<ListItem>>
    var lastPageSize: Int?
    var page: Int
    val isListLoading: MutableLiveData<Boolean>

    fun loadFirstPage() {
        page = 1
        loadItems(1, clearPreviousItems = true)
    }

    fun loadNextPage() {
        if (isListLoading.value.orTrue()) return

        page = page.plus(1)
        if (lastPageSize == PER_PAGE) loadItems(page, clearPreviousItems = false)
    }

    fun loadItems(page: Int, clearPreviousItems: Boolean) {
        val previousItems = itemList.value?.toMutableList()?.apply { removeFirstIf { it is LoadingItem } } ?: mutableListOf()
        if (clearPreviousItems) previousItems.clear()

        launchViewModelScope {
            val itemsResponse = fetchItems(page, PER_PAGE)
            lastPageSize = itemsResponse.size

            itemList.value = previousItems.apply {
                addAll(itemsResponse)
                if (itemsResponse.size == PER_PAGE) add(LoadingItem())
            }.distinct()
            isListLoading.value = false
        }
    }

    suspend fun fetchItems(page: Int, perPage: Int): List<ListItem>
}
