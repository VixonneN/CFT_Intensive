package com.khomichenko.sergey.homework1410.data.auth.data_source

import com.khomichenko.sergey.homework1410.domain.entity.auth.RegistrationResponseEntity

data class RegistrationResponse(
    val name: String,
    val role: String
) {
    fun toEntity() : RegistrationResponseEntity =
        RegistrationResponseEntity(name, role)
}