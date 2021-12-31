package com.khomichenko.sergey.homework1410.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khomichenko.sergey.homework1410.presentation.add_new_loan_screen.view_model.AddNewLoanFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models.AuthFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.registration_screen.view_model.RegistrationFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.loan_information.view_model.LoanInformationViewModel
import com.khomichenko.sergey.homework1410.presentation.main_loan_screen.view_models.MainLoanFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.view_model_factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

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

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(AddNewLoanFragmentViewModel::class)
    fun bindAddNewLoanViewModel(viewModel: AddNewLoanFragmentViewModel) : ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(LoanInformationViewModel::class)
    fun bindLoanInformationViewModel(viewModel: LoanInformationViewModel) : ViewModel


    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(AuthFragmentViewModel::class)
    fun bindAuthViewModel(viewModel: AuthFragmentViewModel) : ViewModel
}