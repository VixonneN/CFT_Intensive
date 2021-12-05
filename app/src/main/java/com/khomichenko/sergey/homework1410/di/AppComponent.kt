package com.khomichenko.sergey.homework1410.di

import android.content.Context
import com.khomichenko.sergey.homework1410.di.data.network.NetworkModule
import com.khomichenko.sergey.homework1410.di.domain.DataModule
import com.khomichenko.sergey.homework1410.di.presentation.PresentationModule
import com.khomichenko.sergey.homework1410.presentation.auth_screen.fragment.AuthFragment
import com.khomichenko.sergey.homework1410.presentation.auth_screen.fragment.RegistrationFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, PresentationModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : AppComponent
    }

    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthFragment)
}