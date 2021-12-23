package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
import com.khomichenko.sergey.homework1410.domain.entity.auth.RegistrationResponseEntity
import com.khomichenko.sergey.homework1410.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterRequestUseCase @Inject constructor(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(authEntity: AuthEntity): RegistrationResponseEntity =
        repository.register(authEntity)

}