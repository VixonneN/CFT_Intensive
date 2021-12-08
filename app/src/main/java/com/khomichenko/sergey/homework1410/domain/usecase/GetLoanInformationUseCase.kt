package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import retrofit2.Call
import java.io.IOException
import javax.inject.Inject

class GetLoanInformationUseCase @Inject constructor(
    private val repository: LoanRepository,
) {

    suspend operator fun invoke(id: Int): Call<LoanDTO> =
        repository.getLoanInformation(id)
}