package com.khomichenko.sergey.homework1410.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.khomichenko.sergey.homework1410.data.dto.LoanDTO
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanState
import java.time.OffsetDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun LoanDTO.toLoanEntity(): LoanEntity =
    LoanEntity(
        amount,
        OffsetDateTime.parse(date),
        firstName,
        id,
        lastName,
        percent,
        period,
        phoneNumber,
        when (state) {
            "APPROVED" -> LoanState.APPROVED
            "REGISTERED" -> LoanState.REGISTERED
            "REJECTED" -> LoanState.REJECTED
            else -> throw IllegalArgumentException()
        }
    )

@RequiresApi(Build.VERSION_CODES.O)
fun List<LoanDTO>.toEntity() : List<LoanEntity> =
    this.map {
        it.toLoanEntity()
    }