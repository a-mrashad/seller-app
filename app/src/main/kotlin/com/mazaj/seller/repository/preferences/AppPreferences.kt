package com.mazaj.seller.repository.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.mazaj.seller.extensions.orFalse
import com.mazaj.seller.extensions.orTrue
import java.util.*

const val FILE_NAME = "Example Application"

object AppPreferences {
    private var sharedPreferences: SharedPreferences? = null

    fun setup(context: Context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)
    }

    fun clear() = sharedPreferences?.edit { clear() }

    var networkAvailable: Boolean
        get() = Key.NETWORK_AVAILABLE.getBoolean().orFalse()
        set(value) = Key.NETWORK_AVAILABLE.setBoolean(value)

    var isFirstTime: Boolean
        get() = Key.IS_FIRST_TIME.getBoolean().orTrue()
        set(value) = Key.IS_FIRST_TIME.setBoolean(value)

    var currentLanguage: String
        get() = Key.CURRENT_LANGUAGE.getString() ?: Locale.getDefault().toString()
        set(value) = Key.CURRENT_LANGUAGE.setString(value)

    var token: String?
        get() = Key.TOKEN.getString().orEmpty()
        set(value) = Key.TOKEN.setString(value)

    var refreshToken: String?
        get() = Key.REFRESH_TOKEN.getString().orEmpty()
        set(value) = Key.REFRESH_TOKEN.setString(value)

    private enum class Key {
        NETWORK_AVAILABLE, TOKEN, REFRESH_TOKEN, CURRENT_LANGUAGE, IS_FIRST_TIME;

        fun getBoolean(): Boolean? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getBoolean(name, false) else null
        fun getFloat(): Float? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getFloat(name, 0f) else null
        fun getInt(): Int? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getInt(name, 0) else null
        fun getLong(default: Long = 0): Long? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getLong(name, default) else null
        fun getString(): String? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getString(name, "") else null

        fun setBoolean(value: Boolean?) = value?.let { sharedPreferences!!.edit { putBoolean(name, value) } } ?: remove()
        fun setFloat(value: Float?) = value?.let { sharedPreferences!!.edit { putFloat(name, value) } } ?: remove()
        fun setInt(value: Int?) = value?.let { sharedPreferences!!.edit { putInt(name, value) } } ?: remove()
        fun setLong(value: Long?) = value?.let { sharedPreferences!!.edit { putLong(name, value) } } ?: remove()
        fun setString(value: String?) = value?.let { sharedPreferences!!.edit { putString(name, value) } } ?: remove()

        fun exists(): Boolean = sharedPreferences!!.contains(name)
        fun remove() = sharedPreferences!!.edit { remove(name) }
    }
}
