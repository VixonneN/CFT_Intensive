package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.domain.entity.AuthEntity
import com.khomichenko.sergey.homework1410.domain.repository.AuthRepository
import retrofit2.Call
import javax.inject.Inject

class LoginRequestUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(authEntity: AuthEntity) : Call<Unit> {
        return repository.logIn(authEntity)
    }
}