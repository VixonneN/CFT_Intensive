package com.khomichenko.sergey.homework1410.data.repository

import com.khomichenko.sergey.homework1410.data.data_source.Api
import com.khomichenko.sergey.homework1410.data.main_loan.ConditionsDTO
import com.khomichenko.sergey.homework1410.data.main_loan.CreateLoanBody
import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.CreateLoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val api: Api,
) : LoanRepository {

    override suspend fun getAllLoan(): Call<List<LoanDTO>> =
        api.getAllLoans()

    override suspend fun createNewLoan(createLoanEntity: CreateLoanEntity): Call<LoanDTO> =
        api.createLoan(createLoanEntity.toBody())

    override suspend fun getConditions(): Call<ConditionsDTO> =
        api.getConditions()
}