package com.mazaj.seller.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.ui.shared.Message
import com.mazaj.seller.ui.shared.network.NetworkStatusViewModel
import com.mazaj.seller.ui.shared.network.NetworkViewModel

open class BaseViewModel(application: Application) : AndroidViewModel(application), NetworkViewModel, NetworkStatusViewModel {
    override val coroutineScope = viewModelScope
    override val networkAvailability = MutableLiveData<Boolean>()
    override val hideInternetStatus = SingleLiveEvent<Void>()
    override val messageLiveData = SingleLiveEvent<Message>()
    override val isFormLoading = MutableLiveData<Boolean>()
    val isScreenLoading = MutableLiveData<Boolean>()
    val uploadProgressLiveData = MutableLiveData<Int>()

    init { onViewCreated() }
}
