package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.domain.entity.main_loan.ConditionsEntity
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import javax.inject.Inject

class GetConditionsUseCase @Inject constructor(
    private val repository: LoanRepository
) {

    suspend operator fun invoke() : ConditionsEntity {
        return repository.getConditions()
    }
}