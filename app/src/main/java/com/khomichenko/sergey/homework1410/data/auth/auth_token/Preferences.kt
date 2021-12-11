package com.khomichenko.sergey.homework1410.data.auth.auth_token

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        preferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    fun deleteToken() {
        preferences.edit().remove(ACCESS_TOKEN_KEY).apply()
    }

    fun setInitUser(init: Boolean) {
        preferences.edit().putBoolean(INIT_USER, init).apply()
    }

    fun getInitUser() : Boolean =
        preferences.getBoolean(INIT_USER, false)

    val savedToken: String
        get() = preferences.getString(ACCESS_TOKEN_KEY, "")!!

    companion object {
        private const val INIT_USER = "initUser"
        private const val PREFERENCES_NAME = "token_preferences"
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
    }
}