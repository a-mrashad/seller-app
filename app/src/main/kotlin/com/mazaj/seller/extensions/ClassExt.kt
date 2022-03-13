package com.mazaj.seller.extensions

import java.util.*

fun <T : Any> T.classSimpleName() = javaClass.simpleName.toLowerCase(Locale.ROOT)
