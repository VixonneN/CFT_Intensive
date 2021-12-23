package com.khomichenko.sergey.homework1410.di

import android.app.Application
import com.khomichenko.sergey.homework1410.data.data_source.shared_preferences.PreferencesProvider

class App : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent() : AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }


    override fun onCreate() {
        super.onCreate()
        PreferencesProvider.initialize(this)
    }

}