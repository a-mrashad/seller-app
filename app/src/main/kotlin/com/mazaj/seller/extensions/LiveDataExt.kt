package com.mazaj.seller.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> LiveData<T>.getOrAwaitValue(awaitTimeout: Long = 2): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val observer = Observer<T> { t ->
        value = t
        latch.countDown()
    }
    observeForever(observer)
    latch.await(awaitTimeout, TimeUnit.SECONDS)
    return value
}

fun <T> MutableLiveData<T>.call() {
    value = null
}
