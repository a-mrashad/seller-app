package com.mazaj.seller.ui.resetPassword.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mazaj.seller.Constants.MIN_PASSWORD_LENGTH
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.common.SingleLiveEvent
import com.mazaj.seller.extensions.isPresent
import com.mazaj.seller.repository.networking.models.ResetPasswordBody
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.shared.ErrorMessage

class ResetPasswordViewModel(application: Application) : BaseViewModel(application) {
    val onPasswordResetSuccessfully = SingleLiveEvent<Void>()
    var enableResetButtonLiveData = MutableLiveData<Boolean>()

    var password: String? = null
        set(value) {
            field = value
            onFieldUpdated()
        }
    var passwordConfirmation: String? = null
        set(value) {
            field = value
            onFieldUpdated()
        }

    fun resetPassword(email: String?, token: String?) = launchViewModelScope {
        if (password != passwordConfirmation) {
            messageLiveData.value = ErrorMessage("Password and confirmation must match")
            return@launchViewModelScope
        }
        if (password?.length!! < MIN_PASSWORD_LENGTH) {
            messageLiveData.value = ErrorMessage("Password must be more than 8")
            return@launchViewModelScope
        }
        isFormLoading.value = true
        repository.resetPassword(ResetPasswordBody(
            email = email!!,
            token = token!!,
            password = password!!,
            passwordConfirmation = passwordConfirmation!!
        ))
        isFormLoading.value = false
        onPasswordResetSuccessfully.call()
    }

    private fun onFieldUpdated() { enableResetButtonLiveData.value = passwordConfirmation.isPresent() && password.isPresent() }
}
