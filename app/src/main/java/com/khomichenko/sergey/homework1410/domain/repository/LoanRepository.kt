package com.khomichenko.sergey.homework1410.domain.repository

import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import retrofit2.Call

interface LoanRepository {

    suspend fun getAllLoan(): Call<List<LoanDTO>>
}