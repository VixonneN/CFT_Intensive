package com.khomichenko.sergey.homework1410.data.auth.repository

import com.khomichenko.sergey.homework1410.data.auth.data_source.RegistrationResponse
import com.khomichenko.sergey.homework1410.data.auth.network.NetworkSettings
import com.khomichenko.sergey.homework1410.data.mappers.AuthMapper
import com.khomichenko.sergey.homework1410.domain.entity.AuthEntity
import com.khomichenko.sergey.homework1410.domain.repository.AuthRepository
import retrofit2.Call
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val networkSettings = NetworkSettings()

    override suspend fun logIn(authEntity: AuthEntity) : Call<String> =
        networkSettings.api().logIn(AuthMapper.toAuthBody(authEntity))


    override suspend fun register(authEntity: AuthEntity) : Call<RegistrationResponse> =
        networkSettings.api().register(AuthMapper.toAuthBody(authEntity))

}