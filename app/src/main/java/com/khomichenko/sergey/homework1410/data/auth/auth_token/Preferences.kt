package com.khomichenko.sergey.homework1410.data.auth.auth_token

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        preferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    val savedToken: String
        get() = preferences.getString(ACCESS_TOKEN_KEY, "")!!

    companion object {
        private const val PREFERENCES_NAME = "token_preferences"
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
    }

}