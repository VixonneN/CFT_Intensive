package com.khomichenko.sergey.homework1410.data.auth.repository

import com.khomichenko.sergey.homework1410.data.auth.data_source.Api
import com.khomichenko.sergey.homework1410.data.auth.data_source.RegistrationResponse
import com.khomichenko.sergey.homework1410.data.mappers.toAuthBody
import com.khomichenko.sergey.homework1410.domain.entity.AuthEntity
import com.khomichenko.sergey.homework1410.domain.repository.AuthRepository
import retrofit2.Call
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: Api
) : AuthRepository {


    override suspend fun logIn(authEntity: AuthEntity) : Call<String> =
        api.logIn(toAuthBody(authEntity))


    override suspend fun register(authEntity: AuthEntity) : Call<RegistrationResponse> =
        api.register(toAuthBody(authEntity))

}