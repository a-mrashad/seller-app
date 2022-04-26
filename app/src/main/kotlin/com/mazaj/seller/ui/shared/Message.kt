package com.mazaj.seller.ui.shared

import android.content.Context

sealed class Message(open val messageString: String? = null, open val messageRes: Int? = null, open val value: String? = null) {
    fun toString(context: Context? = null) = if (value.isNullOrBlank()) {
        if (messageString.isNullOrBlank()) context?.resources?.getString(messageRes!!) else messageString
    } else {
        context?.resources?.getString(messageRes!!, value)
    }
}

data class SuccessMessage(override val messageString: String? = null, override val messageRes: Int? = null, override val value: String? = null) : Message()
data class ErrorMessage(override val messageString: String? = null, override val messageRes: Int? = null, override val value: String? = null) : Message()
data class CustomizedErrorMessage(
    override val messageString: String? =
null,
    override val messageRes: Int? = null,
    override val value: String? = null
) : Message()
