package com.khomichenko.sergey.homework1410.data.main_loan

import com.khomichenko.sergey.homework1410.domain.entity.main_loan.ConditionsEntity

data class ConditionsDTO(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
) {
    fun toEntity() : ConditionsEntity =
        ConditionsEntity(maxAmount, percent, period)
}
