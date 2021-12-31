package com.khomichenko.sergey.homework1410.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.khomichenko.sergey.homework1410.domain.repository.SharedRepository

//TODO проверить DI, запровайдить контекст
class SharedRepositoryImpl(context: Context) : SharedRepository {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val INIT_USER = "initUser"
        private const val PREFERENCES_NAME = "token_preferences"
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        private const val CURRENT_THEME = "current_theme"
    }

    override fun saveToken(token: String) {
        preferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    override fun getSavedToken(): String =
        preferences.getString(ACCESS_TOKEN_KEY, "")!!


    override fun deleteToken() {
        preferences.edit().remove(ACCESS_TOKEN_KEY).apply()
    }

    override fun setUnitUser(init: Boolean) {
        preferences.edit().putBoolean(INIT_USER, init).apply()
    }

    override fun getUnitUser(): Boolean =
        preferences.getBoolean(INIT_USER, false)

    override fun setTheme(theme: Int) {
        preferences.edit().putInt(CURRENT_THEME, theme).apply()
    }

    override fun getTheme(): Int =
        preferences.getInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_NO)
}