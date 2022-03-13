package com.mazaj.seller.extensions

fun Throwable.throwIf(predicate: (Throwable) -> Boolean) {
    if (predicate(this)) throw this
}
