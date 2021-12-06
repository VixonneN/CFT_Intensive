package com.khomichenko.sergey.homework1410.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models.RegistrationFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.main_loan_screen.view_models.MainLoanFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.view_model_factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface AuthModule {

    @Binds
    fun bindViewModelFactory(
        factory: ViewModelFactory
    ) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(RegistrationFragmentViewModel::class)
    fun bindRegistrationViewModel(viewModel: RegistrationFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(MainLoanFragmentViewModel::class)
    fun bindMainLoanViewModel(viewModel: MainLoanFragmentViewModel): ViewModel

}