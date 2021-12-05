package com.khomichenko.sergey.homework1410.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models.RegistrationFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface PresentationModule {

    @Binds
    fun bindViewModelFactory(
        factory: ViewModelFactory
    ) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(RegistrationFragmentViewModel::class)
    fun bindViewModel(viewModel: RegistrationFragmentViewModel): ViewModel
}