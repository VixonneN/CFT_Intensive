package com.khomichenko.sergey.homework1410.presentation.convecter

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanState
import com.khomichenko.sergey.homework1410.presentation.entity.LoanPresentation
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class Converter @Inject constructor(
    private val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToPresentation(loanEntity: LoanEntity): LoanPresentation =
        LoanPresentation(
            amount = loanEntity.amount,
            date = DateTimeFormatter.ofPattern("dd.MM.yy").format(
                loanEntity.date.atZoneSameInstant(ZoneId.systemDefault())),
            time = DateTimeFormatter.ofPattern("hh:mm")
                .format(loanEntity.date.atZoneSameInstant(ZoneId.systemDefault())),
            firstName = loanEntity.firstName,
            id = loanEntity.id,
            lastName = loanEntity.lastName,
            percent = loanEntity.percent,
            period = loanEntity.period,
            phoneNumber = loanEntity.phoneNumber,
            state = when (loanEntity.state) {
                LoanState.APPROVED -> context.getString(R.string.accepted)
                LoanState.REGISTERED -> context.getString(R.string.registrated)
                LoanState.REJECTED -> context.getString(R.string.rejected)
            }
        )
}