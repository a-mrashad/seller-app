package com.mazaj.seller.ui.shared

import android.view.View
import androidx.lifecycle.Observer
import com.mazaj.seller.base.BaseView
import com.mazaj.seller.base.BaseViewModel
import com.mazaj.seller.extensions.showError
import com.mazaj.seller.extensions.showSnackBar

interface ShowMessageView : BaseView {
    val viewModel: BaseViewModel

    fun setupShowMessageView() = viewModel.messageLiveData.observe(activity, Observer { message: Message ->
        when (message) {
            is ErrorMessage -> activity.showSnackBar(message = message.toString(activity)!!)
            is CustomizedErrorMessage -> activity.showError(View.VISIBLE, message.toString(activity)!!)
            is SuccessMessage -> activity.showSnackBar(message = message.toString(activity)!!)
        }
    })
}
