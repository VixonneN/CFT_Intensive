package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.CreateLoanEntity
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import retrofit2.Call
import javax.inject.Inject

class CreateNewLoanUseCase @Inject constructor(
    private val repository: LoanRepository
) {

    suspend operator fun invoke(createLoanEntity: CreateLoanEntity) : Call<LoanDTO> =
        repository.createNewLoan(createLoanEntity)
}