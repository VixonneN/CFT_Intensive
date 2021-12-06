package com.khomichenko.sergey.homework1410.domain.entity.main_loan

import java.math.BigDecimal

data class LoanEntity(
    val amount: BigDecimal,
    val date: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String
    )