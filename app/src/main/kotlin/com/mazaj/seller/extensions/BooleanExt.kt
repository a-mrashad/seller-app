package com.mazaj.seller.extensions

fun Boolean?.orFalse(): Boolean = this ?: false
fun Boolean?.orTrue(): Boolean = this ?: true
