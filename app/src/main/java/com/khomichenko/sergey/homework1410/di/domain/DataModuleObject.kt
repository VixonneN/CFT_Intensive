package com.khomichenko.sergey.homework1410.di.domain

import android.content.Context
import com.khomichenko.sergey.homework1410.data.repository.SharedRepositoryImpl
import com.khomichenko.sergey.homework1410.domain.repository.SharedRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModuleObject {

    @Singleton
    @Provides
    fun bindSharedRepository(context: Context) : SharedRepository =
        SharedRepositoryImpl(context)

}