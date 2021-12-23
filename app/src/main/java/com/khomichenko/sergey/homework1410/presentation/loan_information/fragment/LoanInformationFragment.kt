package com.khomichenko.sergey.homework1410.presentation.loan_information.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.data.data_source.shared_preferences.PreferencesProvider
import com.khomichenko.sergey.homework1410.databinding.FragmentLoanInformationBinding
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.presentation.entity.LoanPresentation
import com.khomichenko.sergey.homework1410.presentation.loan_information.view_model.LoanInformationViewModel
import javax.inject.Inject

class LoanInformationFragment : Fragment() {
    private var callback: OnBackPressedCallback? = null

    private var _binding : FragmentLoanInformationBinding? = null
    private val mBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LoanInformationViewModel

    private lateinit var currentLoan: LoanPresentation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoanInformationBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        currentLoan = requireArguments().getSerializable("id_loan") as LoanPresentation
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController().navigate(R.id.action_loanInformationFragment_to_mainLoanFragment)
                viewModel.finishFragment()
            }
        }.also {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, it)
        }
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
        viewModel.currentLoan(currentLoan)
        setData()
        viewModel.exception.observe(this) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setData() {
        viewModel.loanInformation.observe(this) { loanInfo ->

//            val state = viewModel.changeState(loanInfo.state)
            mBinding.lastNameEt.setText(loanInfo.lastName)
            mBinding.firstNameEt.setText(loanInfo.firstName)
            mBinding.mobileNumberEt.setText(loanInfo.phoneNumber)
            mBinding.amountEt.setText(String.format(loanInfo.amount.toString()))
            mBinding.percentEt.setText(loanInfo.percent.toString())
            mBinding.loanStatusEt.setText(loanInfo.state)
            mBinding.periodEt.setText(loanInfo.period.toString())
            mBinding.dateLoanEt.setText(String.format(loanInfo.date))
            setLoanText(loanInfo.state)
        }
    }

    private fun setLoanText(state: String) =
        when (state) {
            getString(R.string.accepted) -> {
                mBinding.loanText.setText(getString(R.string.accepted_loan))
            }
            getString(R.string.registrated) -> {
                mBinding.loanText.setText(getString(R.string.registered_loan))
            }
            getString(R.string.rejected)  -> {
                mBinding.loanText.setText(getString(R.string.rejected_loan))
            }
            else -> Any()
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_btn, menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_refresh -> {
                viewModel.getLoanInformation(currentLoan.id)
            }
            R.id.change_theme -> {
                viewModel.changeTheme(PreferencesProvider.preferences.getTheme())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navController() : NavController =
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}