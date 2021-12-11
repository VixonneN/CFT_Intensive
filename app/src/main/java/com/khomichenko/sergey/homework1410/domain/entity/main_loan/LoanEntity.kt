package com.khomichenko.sergey.homework1410.domain.entity.main_loan

import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import java.math.BigDecimal
import java.util.*

data class LoanEntity(
    val amount: BigDecimal,
    val date: Date,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String
    ){

    fun toLoanDto() : LoanDTO {
        return LoanDTO(
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