package com.khomichenko.sergey.homework1410.presentation.add_new_loan_screen.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.data.auth.auth_token.PreferencesProvider
import com.khomichenko.sergey.homework1410.databinding.FragmentAddNewLoanBinding
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.presentation.add_new_loan_screen.view_model.AddNewLoanFragmentViewModel
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
        setHasOptionsMenu(true)
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

    override fun onStart() {
        super.onStart()
        getConditions()
        mBinding.sendBtn.setOnClickListener {
            getData()
            viewModel.loan.observe(this) {
                Log.d("TAG", "onStart: $it")
            }
        }
        showProgressBar()
        navigate()
        exceptionHandler()
    }

    private fun showProgressBar(){
        viewModel.loading.observe(this) { startLoading ->
            if (startLoading) {
                mBinding.addLoanProgressBar.visibility = View.VISIBLE
                mBinding.sendBtn.isEnabled = false
            } else {
                mBinding.addLoanProgressBar.visibility = View.GONE
                mBinding.sendBtn.isEnabled = true
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

    private fun navigate() {
        viewModel.finished.observe(this) { finished ->
            if (finished) {
                navController().navigate(R.id.action_addNewLoanFragment_to_mainLoanFragment)
            }
        }
    }

    private fun exceptionHandler() {
        viewModel.exception.observe(this){ exceptionText ->
            Toast.makeText(context, exceptionText, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exit_btn, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_exit -> {
                PreferencesProvider.preferences.deleteToken()
                PreferencesProvider.preferences.setInitUser(false)
                navController().navigate(R.id.action_addNewLoanFragment_to_registrationFragment)
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