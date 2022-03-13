package com.mazaj.seller.extensions

fun <T> MutableList<T>.removeFirstIf(predicate: (T) -> Boolean): MutableList<T>? {
    val index = indexOfFirst(predicate)
    return if (index == -1) this else this.apply { removeAt(index) }
}

fun <T> MutableList<T>.replaceFirstIf(item: T, predicate: (T) -> Boolean): MutableList<T>? {
    val index = indexOfFirst(predicate)
    return if (index == -1) this else this.apply { set(index, item) }
}

fun <T> MutableList<T>.contains(predicate: (T) -> Boolean): Boolean {
    val index = indexOfFirst(predicate)
    return index != -1
}

fun <T> Collection<T>?.isPresent() = !isNullOrEmpty()
