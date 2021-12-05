package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.data.auth.data_source.RegistrationResponse
import com.khomichenko.sergey.homework1410.domain.entity.AuthEntity
import com.khomichenko.sergey.homework1410.domain.repository.AuthRepository
import retrofit2.Call
import javax.inject.Inject

class RegisterRequestUseCase @Inject constructor(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(authEntity: AuthEntity): Call<RegistrationResponse> =
        repository.register(authEntity)

}