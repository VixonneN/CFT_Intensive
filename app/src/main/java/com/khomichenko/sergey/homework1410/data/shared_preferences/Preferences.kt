package com.khomichenko.sergey.homework1410.data.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class Preferences(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        preferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    val savedToken: String
        get() = preferences.getString(ACCESS_TOKEN_KEY, "")!!

    fun deleteToken() {
        preferences.edit().remove(ACCESS_TOKEN_KEY).apply()
    }

    fun setInitUser(init: Boolean) {
        preferences.edit().putBoolean(INIT_USER, init).apply()
    }

    fun getInitUser() : Boolean =
        preferences.getBoolean(INIT_USER, false)

    fun setTheme(theme: Int) {
        preferences.edit().putInt(CURRENT_THEME, theme).apply()
    }

    fun getTheme(): Int =
        preferences.getInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_NO)


    companion object {
        private const val INIT_USER = "initUser"
        private const val PREFERENCES_NAME = "token_preferences"
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        private const val CURRENT_THEME = "current_theme"
    }
}