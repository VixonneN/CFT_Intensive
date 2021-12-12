package com.khomichenko.sergey.homework1410.presentation.main_loan_screen.fragment.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khomichenko.sergey.homework1410.databinding.LoanItemBinding
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity

class MainLoanAdapter(
    private val onViewClickListener:(LoanEntity) -> Unit
) : RecyclerView.Adapter<MainLoanViewHolder>() {

    var loanList = emptyList<LoanEntity>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainLoanViewHolder {
        val binding = LoanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainLoanViewHolder(binding, onViewClickListener)
    }

    override fun onBindViewHolder(holder: MainLoanViewHolder, position: Int) {
        holder.bind(loanList[position])
    }

    override fun getItemCount(): Int =
        loanList.size
}