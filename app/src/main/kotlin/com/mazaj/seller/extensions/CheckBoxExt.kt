package com.mazaj.seller.extensions

import android.widget.CheckBox

fun CheckBox.revertChecking() {
    isChecked = isChecked.not()
}
