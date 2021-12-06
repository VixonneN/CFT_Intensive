package com.khomichenko.sergey.homework1410.presentation.main_loan_screen.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.khomichenko.sergey.homework1410.databinding.FragmentMainLoanBinding
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.presentation.main_loan_screen.fragment.recycler_view.MainLoanAdapter
import com.khomichenko.sergey.homework1410.presentation.main_loan_screen.view_models.MainLoanFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main_loan.*
import javax.inject.Inject

class MainLoanFragment : Fragment() {

    private var _binding: FragmentMainLoanBinding? = null
    private val mBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainLoanFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainLoanBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[MainLoanFragmentViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllLoans()
        floatingButton()
        addDataRecycler()
    }

    private fun addDataRecycler(){
        mBinding.loanRecyclerView.apply {
            val loanAdapter = MainLoanAdapter(
                onViewClickListener = {
                    Toast.makeText(context, "bnt_adapter", Toast.LENGTH_SHORT).show()
                }
            )
            viewModel.allLoans.observe(this@MainLoanFragment) {
                loanAdapter.loanList = it
            }
            adapter = loanAdapter
        }

    }

    private fun floatingButton() {
        mBinding.addLoanBtn.setOnClickListener {
//            navigation.navigate()
        }
    }
}