package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import retrofit2.Call
import javax.inject.Inject

class GetAllLoansUseCase @Inject constructor(
    private val repository: LoanRepository
) {

    suspend operator fun invoke() : Call<List<LoanDTO>> =
        repository.getAllLoan()
}