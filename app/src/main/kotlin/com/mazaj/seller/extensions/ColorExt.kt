package com.mazaj.seller.extensions

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat

fun Int.toRGB(context: Context): Int {
    val colorValue = ContextCompat.getColor(context, this)
    return Color.rgb(Color.red(colorValue), Color.green(colorValue), Color.blue(colorValue))
}
