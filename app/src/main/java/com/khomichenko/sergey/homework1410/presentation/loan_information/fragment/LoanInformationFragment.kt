package com.khomichenko.sergey.homework1410.presentation.loan_information.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.data.auth.auth_token.PreferencesProvider
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
        setHasOptionsMenu(true)
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
            mBinding.periodEt.setText(loanInfo.period.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exit_btn, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_exit -> {
                PreferencesProvider.preferences.deleteToken("ACCESS_TOKEN_KEY")
                PreferencesProvider.preferences.setInitUser(false)
                navController().navigate(R.id.action_loanInformationFragment_to_registrationFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navController() : NavController =
        findNavController()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}