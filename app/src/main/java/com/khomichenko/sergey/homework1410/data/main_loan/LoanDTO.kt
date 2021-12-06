package com.khomichenko.sergey.homework1410.data.main_loan

import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import java.math.BigDecimal

data class LoanDTO(
    val amount: BigDecimal,
    val date: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String
) {
    fun toLoanEntity() : LoanEntity {
        return LoanEntity(
            amount,
            date,
            firstName,
            id,
            lastName,
            percent,
            period,
            phoneNumber,
            state
        )
    }
}