package com.khomichenko.sergey.homework1410.data.data_source.shared_preferences

import android.content.Context
import androidx.annotation.MainThread
import java.lang.IllegalStateException

object PreferencesProvider {
    private var sPreferences: Preferences? = null
    @MainThread
    fun initialize(context: Context) {
        if (sPreferences == null) {
            sPreferences = Preferences(context)
        } else {
            throw IllegalStateException("You shouldn't call initialize twice")
        }
    }

    val preferences: Preferences
        get() {
            checkNotNull(sPreferences) { "You should call initialize before accessing Preferences" }
            return sPreferences!!
        }
}