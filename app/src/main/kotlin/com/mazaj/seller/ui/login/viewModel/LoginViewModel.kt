package com.mazaj.seller.ui.login.viewModel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.Constants.DELAY_TIME_2
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.extensions.isPresent
import com.mazaj.seller.repository.preferences.AppPreferences
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.shared.ErrorMessage
import kotlinx.coroutines.delay

class LoginViewModel(application: Application) : BaseViewModel(application) {
    var enableLoginButtonLiveData = MutableLiveData<Boolean>()
    val onLoginSucceededLiveEvent = SingleLiveEvent<Void>()
    val loginErrorsLiveEvent = SingleLiveEvent<Void>()
    val clearErrorsLiveEvent = SingleLiveEvent<Void>()

    var email: String? = null
        set(value) {
            field = value
            onFieldUpdated()
        }
    var password: String? = null
        set(value) {
            field = value
            onFieldUpdated()
        }

    private fun onFieldUpdated() { enableLoginButtonLiveData.value = email.isPresent() && password.isPresent() }

    fun onLoginClicked() = launchViewModelScope {
        clearErrorsLiveEvent.call()
        if (!Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches() || password?.length ?: 0 < MIN_PASSWORD_LENGTH) {
            messageLiveData.value = ErrorMessage(messageRes = R.string.invalid_email_password)
            loginErrorsLiveEvent.call()
            return@launchViewModelScope
        }
        isFormLoading.value = true
//        repository.authenticateUser(email!!, password!!)
//        onLoginSucceededLiveEvent.call()
        delay(DELAY_TIME_2)
        if (email != "seller@mazaj.app") {
            messageLiveData.value = ErrorMessage(messageRes = R.string.invalid_credentials)
            loginErrorsLiveEvent.call()
        } else {
            AppPreferences.token = "test"
            onLoginSucceededLiveEvent.call()
        }
        isFormLoading.value = false
    }

    companion object { const val MIN_PASSWORD_LENGTH = 8 }
}
