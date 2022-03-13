package com.mazaj.seller.ui.forgetPassword.viewModel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.Constants
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.extensions.isPresent
import kotlinx.coroutines.delay

class ForgetPasswordViewModel(application: Application) : BaseViewModel(application) {
    var enableLoginButtonLiveData = MutableLiveData<Boolean>()
    val onForgetPasswordSucceeded = SingleLiveEvent<Void>()
    val forgetPasswordErrorsLiveEvent = SingleLiveEvent<Int>()
    val clearErrorsLiveEvent = SingleLiveEvent<Void>()

    var email: String? = null
        set(value) {
            field = value
            onFieldUpdated()
        }

    private fun onFieldUpdated() { enableLoginButtonLiveData.value = email.isPresent() }

    fun onForgetPasswordClicked() = launchViewModelScope {
        isFormLoading.value = true
        clearErrorsLiveEvent.call()
        if (!Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()) {
            forgetPasswordErrorsLiveEvent.value = R.string.invalid_email
            return@launchViewModelScope
        }
        delay(Constants.DELAY_TIME_2)
        isFormLoading.value = false
        onForgetPasswordSucceeded.call()
    }
}
