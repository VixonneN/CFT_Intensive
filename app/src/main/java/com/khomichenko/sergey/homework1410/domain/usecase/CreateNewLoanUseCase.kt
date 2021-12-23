package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.domain.entity.main_loan.CreateLoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import javax.inject.Inject

class CreateNewLoanUseCase @Inject constructor(
    private val repository: LoanRepository
) {

    suspend operator fun invoke(createLoanEntity: CreateLoanEntity) : LoanEntity =
        repository.createNewLoan(createLoanEntity)
}