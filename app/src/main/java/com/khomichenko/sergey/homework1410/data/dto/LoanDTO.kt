package com.khomichenko.sergey.homework1410.data.dto

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

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
)