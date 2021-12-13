package com.khomichenko.sergey.homework1410.di

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.khomichenko.sergey.homework1410.data.auth.auth_token.PreferencesProvider

class App : Application() {

    private companion object{
        const val NIGHT_MODE = AppCompatDelegate.MODE_NIGHT_YES
        const val DAY_MODE = AppCompatDelegate.MODE_NIGHT_NO
    }

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent() : AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }


    override fun onCreate() {
        super.onCreate()
        PreferencesProvider.initialize(this)
        setNightTheme()
    }

    private fun setNightTheme(){

    }
}