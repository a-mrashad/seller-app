package com.mazaj.seller.extensions

import android.content.Intent

fun Intent.newTask(): Intent {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    return this
}
