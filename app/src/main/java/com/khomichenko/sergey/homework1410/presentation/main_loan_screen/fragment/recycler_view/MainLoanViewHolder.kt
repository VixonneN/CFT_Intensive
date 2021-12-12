package com.khomichenko.sergey.homework1410.presentation.main_loan_screen.fragment.recycler_view

import androidx.recyclerview.widget.RecyclerView
import com.khomichenko.sergey.homework1410.databinding.LoanItemBinding
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity

class MainLoanViewHolder(
    private val binding: LoanItemBinding,
    private val onViewClickListener: (LoanEntity) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: LoanEntity) {
        binding.dateLoanTv.text = String.format(model.date.toString())
        "${model.firstName} ${model.lastName}".also { binding.fioLoanerTv.text = it }
        binding.loanStatusTv.text = changeState(model.state)
        binding.loanAmountTv.text = String.format(model.amount.toString())

        binding.itemContainer.setOnClickListener {
            onViewClickListener(model)
        }
    }

    private fun changeState(state: String): String {
        return when (state) {
            "REJECTED" -> state.replace("REJECTED", "Отклонено")
            "REGISTERED" -> state.replace("REGISTERED", "Зарегистрировано")
            "APPROVED" -> state.replace("APPROVED", "Одобрено")
            else -> ""
        }
    }
}