package com.khomichenko.sergey.homework1410.data.mappers

import com.khomichenko.sergey.homework1410.data.dto.LoanDTO
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity

fun LoanDTO.toLoanEntity(): LoanEntity =
    LoanEntity(
        amount, date, firstName, id, lastName, percent, period, phoneNumber, state
    )

fun List<LoanDTO>.toEntity() : List<LoanEntity> =
    this.map {
        it.toLoanEntity()
    }