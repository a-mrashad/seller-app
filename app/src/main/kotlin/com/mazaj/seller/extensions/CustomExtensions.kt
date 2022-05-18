package com.mazaj.seller.extensions

import com.mazaj.seller.Constants.MINUTE

@Suppress("all")
fun Int.getMonthName(): String = when (this) {
    0 -> "January"
    1 -> "February"
    2 -> "March"
    3 -> "April"
    4 -> "May"
    5 -> "June"
    6 -> "July"
    7 -> "August"
    8 -> "September"
    9 -> "October"
    10 -> "November"
    else -> "December"
}

@Suppress("all")
fun Long.toHoursAndMinutes(): String {
    if (this < 0) return "0 mins"
    var result = ""
    val hours = this / 3_600_000L
    if (hours > 0) result += "$hours hours "
    val remaining = this % 3_600_000L
    val minutes = remaining / MINUTE
    if (minutes > 0) result += "$minutes mins"
    return if (result.isEmpty()) "0 mins" else result
}

@Suppress("all")
fun Long.toHoursOrMinutes(): String {
    if (this < 0) return "0 mins"
    val minutes = this / MINUTE
    return if (minutes < 100) "$minutes mins" else "${this / 3_600_000L} hours"
}
