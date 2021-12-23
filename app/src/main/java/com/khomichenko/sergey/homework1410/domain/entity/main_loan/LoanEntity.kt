package com.khomichenko.sergey.homework1410.domain.entity.main_loan

import com.khomichenko.sergey.homework1410.data.dto.LoanDTO
import java.io.Serializable
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

data class LoanEntity(
    val amount: BigDecimal,
    val date: OffsetDateTime,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: LoanState
    ): Serializable

enum class LoanState {
    APPROVED, REGISTERED, REJECTED
}