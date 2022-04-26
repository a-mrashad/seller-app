package com.mazaj.seller.ui.login.viewModel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mazaj.seller.Constants.MIN_PASSWORD_LENGTH
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.extensions.isPresent
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.shared.CustomizedErrorMessage

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

    fun onLoginClicked() {
        clearErrorsLiveEvent.call()
        if (repository.appPreferences.fcmToken.isNullOrEmpty()) {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        messageLiveData.value = CustomizedErrorMessage(messageRes = R.string.no_internet)
                        return@OnCompleteListener
                    }
                    val token = task.result?.token
                    repository.appPreferences.fcmToken = token
                    login()
                })
        } else {
            login()
        }
    }

    private fun login() = launchViewModelScope {
        if (!Patterns.EMAIL_ADDRESS.matcher(email.toString().trim()).matches() || password?.length ?: 0 < MIN_PASSWORD_LENGTH) {
            messageLiveData.value = CustomizedErrorMessage(messageRes = R.string.invalid_email_password)
            loginErrorsLiveEvent.call()
            return@launchViewModelScope
        }
        val fcmToken = repository.appPreferences.fcmToken ?: return@launchViewModelScope
        isFormLoading.value = true
        repository.authenticateUser(email!!, password!!, fcmToken)
        isFormLoading.value = false
        onLoginSucceededLiveEvent.call()
    }
}
