package com.mazaj.seller.utils

import android.content.DialogInterface

data class AlertDialogContent(
    val body: String,
    val title: String,
    val cancelable: Boolean,
    val positiveText: String,
    val negativeText: String,
    val yesListener: DialogInterface.OnClickListener?,
    val noListener: DialogInterface.OnClickListener?
)
