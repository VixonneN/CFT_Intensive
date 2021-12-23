package com.khomichenko.sergey.homework1410.data.mappers

import com.khomichenko.sergey.homework1410.data.dto.AuthBody
import com.khomichenko.sergey.homework1410.data.dto.ConditionsDTO
import com.khomichenko.sergey.homework1410.data.dto.RegistrationResponse
import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
import com.khomichenko.sergey.homework1410.domain.entity.auth.RegistrationResponseEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.ConditionsEntity

fun AuthEntity.toAuthBody(): AuthBody =
    AuthBody(
        name, password
    )

fun RegistrationResponse.toEntity() : RegistrationResponseEntity =
    RegistrationResponseEntity(name, role)

fun RegistrationResponseEntity.toDTO() : RegistrationResponse =
    RegistrationResponse(name, role)

fun ConditionsDTO.toEntity() : ConditionsEntity =
    ConditionsEntity(maxAmount, percent, period)