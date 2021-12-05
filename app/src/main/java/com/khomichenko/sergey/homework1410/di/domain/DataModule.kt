package com.khomichenko.sergey.homework1410.di.domain

import com.khomichenko.sergey.homework1410.data.auth.repository.AuthRepositoryImpl
import com.khomichenko.sergey.homework1410.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl) : AuthRepository
}