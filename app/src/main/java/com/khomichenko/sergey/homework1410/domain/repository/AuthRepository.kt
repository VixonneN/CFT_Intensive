package com.khomichenko.sergey.homework1410.domain.repository

import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
import com.khomichenko.sergey.homework1410.domain.entity.auth.RegistrationResponseEntity

interface AuthRepository {

    suspend fun logIn(authEntity: AuthEntity): String
    suspend fun register(authEntity: AuthEntity): RegistrationResponseEntity
}