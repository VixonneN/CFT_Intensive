package com.khomichenko.sergey.homework1410.presentation.main_loan_screen.fragment.recycler_view

import androidx.recyclerview.widget.RecyclerView
import com.khomichenko.sergey.homework1410.databinding.LoanItemBinding
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity

class MainLoanViewHolder(private val binding: LoanItemBinding,
private val onViewClickListener:() -> Unit)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: LoanEntity) {
        binding.dateLoanTv.text = model.date
        binding.fioLoanerTv.text = "${model.firstName} ${model.lastName}"
        binding.loanStatusTv.text = model.state
        binding.loanAmountTv.text = String.format(model.amount.toString())

        binding.itemContainer.setOnClickListener {
            onViewClickListener()
        }
    }
}