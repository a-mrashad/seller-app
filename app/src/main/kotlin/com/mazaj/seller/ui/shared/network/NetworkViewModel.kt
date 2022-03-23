package com.mazaj.seller.ui.shared.network

import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.R
import com.mazaj.seller.common.ErrorResponseException
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.extensions.throwIf
import com.mazaj.seller.repository.networking.models.ErrorBody
import com.mazaj.seller.ui.shared.ErrorMessage
import com.mazaj.seller.ui.shared.Message
import java.io.IOException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface NetworkViewModel {
    val coroutineScope: CoroutineScope

    val messageLiveData: SingleLiveEvent<Message>
    val isFormLoading: MutableLiveData<Boolean>

    fun launchViewModelScope(block: suspend CoroutineScope.() -> Unit) {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            exception.throwIf { exception !is IOException }

            var messageResId: Int? = null
            var errorBody: ErrorBody? = null
            when (exception) {
                is ErrorResponseException -> {
                    errorBody = exception.errorMessage as ErrorBody
                    if (errorBody.message == null) messageResId = R.string.something_went_wrong
                }
                is IOException -> messageResId = R.string.network_error
            }

            isFormLoading.value = false
            onFailure(errorBody, messageResId)
        }
        coroutineScope.launch(exceptionHandler, block = block)
    }

    fun onFailure(errorBody: ErrorBody?, messageResId: Int?) {
        messageLiveData.value = ErrorMessage(messageString = errorBody?.message, messageRes = messageResId)
    }
}
