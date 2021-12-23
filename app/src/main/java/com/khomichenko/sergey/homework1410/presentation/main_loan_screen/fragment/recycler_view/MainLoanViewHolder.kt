package com.khomichenko.sergey.homework1410.presentation.main_loan_screen.fragment.recycler_view

import androidx.recyclerview.widget.RecyclerView
import com.khomichenko.sergey.homework1410.databinding.LoanItemBinding
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanState
import com.khomichenko.sergey.homework1410.presentation.entity.LoanPresentation

class MainLoanViewHolder(
    private val binding: LoanItemBinding,
    private val onViewClickListener: (LoanPresentation) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: LoanPresentation) {
        "${model.date} ${model.time}".also { binding.dateLoanTv.text = it }
        "${model.firstName} ${model.lastName}".also { binding.fioLoanerTv.text = it }
        binding.loanStatusTv.text = model.state
        binding.loanAmountTv.text = String.format(model.amount.toString())

        binding.itemContainer.setOnClickListener {
            onViewClickListener(model)
        }
    }
}