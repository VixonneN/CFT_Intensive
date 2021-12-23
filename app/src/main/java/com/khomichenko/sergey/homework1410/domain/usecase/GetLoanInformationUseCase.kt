package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import javax.inject.Inject

class GetLoanInformationUseCase @Inject constructor(
    private val repository: LoanRepository,
) {

    suspend operator fun invoke(id: Int): LoanEntity =
        repository.getLoanInformation(id)
}