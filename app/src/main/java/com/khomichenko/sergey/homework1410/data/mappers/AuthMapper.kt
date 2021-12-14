package com.khomichenko.sergey.homework1410.data.mappers

import com.khomichenko.sergey.homework1410.data.auth.data_source.AuthBody
import com.khomichenko.sergey.homework1410.data.auth.data_source.RegistrationResponse
import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
import com.khomichenko.sergey.homework1410.domain.entity.auth.RegistrationResponseEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity

fun toAuthBody(authEntity: AuthEntity): AuthBody {
    return AuthBody(authEntity.name, authEntity.password)
}
