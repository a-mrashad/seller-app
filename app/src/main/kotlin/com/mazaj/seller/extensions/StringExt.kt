package com.mazaj.seller.extensions

fun String?.ifNotBlank(action: (String?) -> Any) {
    if (!isNullOrBlank()) action(this)
}

fun String?.isPresent() = isNullOrBlank().not()
