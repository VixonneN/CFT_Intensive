package com.khomichenko.sergey.homework1410.presentation.add_new_loan_screen.fragment

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.databinding.FragmentAddNewLoanBinding
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.CreateLoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.presentation.add_new_loan_screen.view_model.AddNewLoanFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.main_loan_screen.view_models.MainLoanFragmentViewModel
import javax.inject.Inject

class AddNewLoanFragment : Fragment() {

    private var _binding: FragmentAddNewLoanBinding? = null
    private val mBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AddNewLoanFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddNewLoanBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[AddNewLoanFragmentViewModel::class.java]
    }

    private fun getConditions() {
        viewModel.getConditions()
        viewModel.conditions.observe(this) {
            mBinding.amountEt.setText(it.maxAmount.toString())
            mBinding.percentEt.setText(it.percent.toString())
            mBinding.periodEt.setText(it.period.toString())
        }
    }

    //TODO добавить прогрессбар и возможность перехода после загрузки на прошлый экран
    override fun onStart() {
        super.onStart()

        getConditions()
        mBinding.sendBtn.setOnClickListener {
            getData()
            viewModel.loan.observe(this) {
                Log.d("TAG", "onStart: $it")
            }
        }

    }

    private fun getData() {
        val lastname = mBinding.lastNameEt.text.toString()
        val firstname = mBinding.firstNameEt.text.toString()
        val amount = mBinding.amountEt.text.toString().toInt()
        val percent = mBinding.percentEt.text.toString().toDouble()
        val period = mBinding.periodEt.text.toString().toInt()
        val number = mBinding.mobileNumberEt.text.toString()
        viewModel.addLoan(amount, firstname, lastname, percent, period, number)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}