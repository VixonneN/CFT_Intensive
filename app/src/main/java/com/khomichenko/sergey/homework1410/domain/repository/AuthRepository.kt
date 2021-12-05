package com.khomichenko.sergey.homework1410.domain.repository

import com.khomichenko.sergey.homework1410.data.auth.data_source.RegistrationResponse
import com.khomichenko.sergey.homework1410.domain.entity.AuthEntity
import retrofit2.Call

interface AuthRepository {

    suspend fun logIn(authEntity: AuthEntity): Call<String>
    suspend fun register(authEntity: AuthEntity): Call<RegistrationResponse>
}