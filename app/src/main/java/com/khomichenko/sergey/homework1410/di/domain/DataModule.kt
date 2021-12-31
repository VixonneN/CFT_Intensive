package com.khomichenko.sergey.homework1410.di.domain

import android.content.Context
import com.khomichenko.sergey.homework1410.data.repository.AuthRepositoryImpl
import com.khomichenko.sergey.homework1410.data.repository.LoanRepositoryImpl
import com.khomichenko.sergey.homework1410.data.repository.SharedRepositoryImpl
import com.khomichenko.sergey.homework1410.domain.repository.AuthRepository
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import com.khomichenko.sergey.homework1410.domain.repository.SharedRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl) : AuthRepository

    @Singleton
    @Binds
    fun bindLoanRepository(impl: LoanRepositoryImpl) : LoanRepository

}