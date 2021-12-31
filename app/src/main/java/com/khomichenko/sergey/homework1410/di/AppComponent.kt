package com.khomichenko.sergey.homework1410.di

import android.content.Context
import com.khomichenko.sergey.homework1410.di.data.network.NetworkModule
import com.khomichenko.sergey.homework1410.di.domain.DataModule
import com.khomichenko.sergey.homework1410.di.domain.DataModuleObject
import com.khomichenko.sergey.homework1410.di.presentation.ViewModelModule
import com.khomichenko.sergey.homework1410.presentation.add_new_loan_screen.fragment.AddNewLoanFragment
import com.khomichenko.sergey.homework1410.presentation.auth_screen.fragment.AuthFragment
import com.khomichenko.sergey.homework1410.presentation.registration_screen.fragment.RegistrationFragment
import com.khomichenko.sergey.homework1410.presentation.loan_information.fragment.LoanInformationFragment
import com.khomichenko.sergey.homework1410.presentation.main_loan_screen.fragment.MainLoanFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, ViewModelModule::class, NetworkModule::class, DataModuleObject::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : AppComponent
    }

    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthFragment)
    fun inject(fragment: MainLoanFragment)
    fun inject(fragment: AddNewLoanFragment)
    fun inject(fragment: LoanInformationFragment)
}