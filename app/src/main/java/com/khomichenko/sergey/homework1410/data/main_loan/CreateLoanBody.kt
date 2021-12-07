package com.khomichenko.sergey.homework1410.data.main_loan

import com.khomichenko.sergey.homework1410.domain.entity.main_loan.CreateLoanEntity

data class CreateLoanBody(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
) {
    fun toEntity(): CreateLoanEntity {
        return CreateLoanEntity(
            amount,
            firstName,
            lastName,
            percent,
            period,
            phoneNumber
        )
    }
}
