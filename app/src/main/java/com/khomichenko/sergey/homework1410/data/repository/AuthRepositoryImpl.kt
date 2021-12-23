package com.khomichenko.sergey.homework1410.data.repository

import com.khomichenko.sergey.homework1410.data.data_source.network.Api
import com.khomichenko.sergey.homework1410.data.dto.RegistrationResponse
import com.khomichenko.sergey.homework1410.data.mappers.toAuthBody
import com.khomichenko.sergey.homework1410.data.mappers.toEntity
import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
import com.khomichenko.sergey.homework1410.domain.entity.auth.RegistrationResponseEntity
import com.khomichenko.sergey.homework1410.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: Api
) : AuthRepository {


    override suspend fun logIn(authEntity: AuthEntity) : String =
        withContext(Dispatchers.IO) {
            api.logIn(authEntity.toAuthBody())
        }


    override suspend fun register(authEntity: AuthEntity) : RegistrationResponseEntity =
        withContext(Dispatchers.IO) {
            api.register(authEntity.toAuthBody()).toEntity()
        }

}