package com.khomichenko.sergey.homework1410.domain.repository

import com.khomichenko.sergey.homework1410.domain.entity.main_loan.ConditionsEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.CreateLoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity

interface LoanRepository {

    suspend fun getAllLoan(): List<LoanEntity>
    suspend fun createNewLoan(createLoanEntity: CreateLoanEntity): LoanEntity
    suspend fun getConditions(): ConditionsEntity
    suspend fun getLoanInformation(id: Int): LoanEntity

}