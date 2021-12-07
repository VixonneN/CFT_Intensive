package com.khomichenko.sergey.homework1410.domain.repository

import com.khomichenko.sergey.homework1410.data.main_loan.ConditionsDTO
import com.khomichenko.sergey.homework1410.data.main_loan.CreateLoanBody
import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.CreateLoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import retrofit2.Call

interface LoanRepository {

    suspend fun getAllLoan(): Call<List<LoanDTO>>

    suspend fun createNewLoan(createLoanEntity: CreateLoanEntity): Call<LoanDTO>

    suspend fun getConditions() : Call<ConditionsDTO>
}