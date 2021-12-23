package com.khomichenko.sergey.homework1410.data.dto

data class CreateLoanBody(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
)