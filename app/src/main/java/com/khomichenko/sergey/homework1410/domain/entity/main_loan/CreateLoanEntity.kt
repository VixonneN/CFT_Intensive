package com.khomichenko.sergey.homework1410.domain.entity.main_loan

import com.khomichenko.sergey.homework1410.data.dto.CreateLoanBody

data class CreateLoanEntity(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String
) {
    fun toBody() : CreateLoanBody {
        return CreateLoanBody(amount, firstName, lastName, percent, period, phoneNumber)
    }
}
