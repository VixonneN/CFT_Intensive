package com.khomichenko.sergey.homework1410.presentation.loan_information.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.khomichenko.sergey.homework1410.databinding.FragmentLoanInformationBinding
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.presentation.loan_information.view_model.LoanInformationViewModel
import javax.inject.Inject

class LoanInformationFragment : Fragment() {

    private var _binding : FragmentLoanInformationBinding? = null
    private val mBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LoanInformationViewModel

    private var idLoan: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoanInformationBinding.inflate(layoutInflater, container, false)
        idLoan = requireArguments().getInt("id_loan")
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[LoanInformationViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        viewModel.getLoanInformation(idLoan)
        viewModel.exception.observe(this) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        setData()
    }

    private fun setData() {
        viewModel.loanInformation.observe(this) { loanInfo ->
            mBinding.lastNameEt.setText(loanInfo.lastName)
            mBinding.firstNameEt.setText(loanInfo.firstName)
            mBinding.mobileNumberEt.setText(loanInfo.phoneNumber)
            mBinding.amountEt.setText(String.format(loanInfo.amount.toString()))
            mBinding.percentEt.setText(loanInfo.percent.toString())
            mBinding.loanStatusEt.setText(loanInfo.state)
        }
    }
}