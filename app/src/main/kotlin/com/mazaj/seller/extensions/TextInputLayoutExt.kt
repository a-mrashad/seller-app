package com.mazaj.seller.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.markAsError(errorHint: String = " ") {
    error = errorHint
}
