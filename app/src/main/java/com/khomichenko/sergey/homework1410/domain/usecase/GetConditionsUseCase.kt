package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.data.main_loan.ConditionsDTO
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import retrofit2.Call
import javax.inject.Inject

class GetConditionsUseCase @Inject constructor(
    private val repository: LoanRepository
) {

    suspend operator fun invoke() : Call<ConditionsDTO> {
        return repository.getConditions()
    }
}