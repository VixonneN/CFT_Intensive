package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import javax.inject.Inject

class GetAllLoansUseCase @Inject constructor(
    private val repository: LoanRepository
) {

    suspend operator fun invoke() : List<LoanEntity> =
        repository.getAllLoan()
}