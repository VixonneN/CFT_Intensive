package com.khomichenko.sergey.homework1410.presentation.entity

import java.io.Serializable
import java.math.BigDecimal

data class LoanPresentation(
    val amount: BigDecimal,
    val date: String,
    val time: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String
) : Serializable
