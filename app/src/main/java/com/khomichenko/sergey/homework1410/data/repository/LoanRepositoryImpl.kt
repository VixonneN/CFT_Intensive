package com.khomichenko.sergey.homework1410.data.repository

import com.khomichenko.sergey.homework1410.data.data_source.network.Api
import com.khomichenko.sergey.homework1410.data.mappers.toEntity
import com.khomichenko.sergey.homework1410.data.mappers.toLoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.ConditionsEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.CreateLoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.repository.LoanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val api: Api,
) : LoanRepository {

    override suspend fun getAllLoan(): List<LoanEntity> =
        withContext(Dispatchers.IO) {
            api.getAllLoans().toEntity()
        }

    override suspend fun createNewLoan(createLoanEntity: CreateLoanEntity): LoanEntity =
        withContext(Dispatchers.IO) {
            api.createLoan(createLoanEntity.toBody()).toLoanEntity()
        }

    override suspend fun getConditions(): ConditionsEntity =
        withContext(Dispatchers.IO) {
            api.getConditions().toEntity()
        }


    override suspend fun getLoanInformation(id: Int): LoanEntity =
        withContext(Dispatchers.IO) {
            api.getLoanInformation(id).toLoanEntity()
        }

}