package com.khomichenko.sergey.homework1410.data.auth.repository

import com.khomichenko.sergey.homework1410.data.data_source.Api
import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import retrofit2.Call
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val api: Api,
) : LoanRepository {

    override suspend fun getAllLoan(): Call<List<LoanDTO>> =
        api.getAllLoans()

}