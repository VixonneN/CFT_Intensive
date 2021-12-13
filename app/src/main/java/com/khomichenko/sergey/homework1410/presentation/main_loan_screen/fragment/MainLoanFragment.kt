package com.khomichenko.sergey.homework1410.presentation.main_loan_screen.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.work.WorkManager
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.data.auth.auth_token.PreferencesProvider
import com.khomichenko.sergey.homework1410.databinding.FragmentMainLoanBinding
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.presentation.main_loan_screen.fragment.recycler_view.MainLoanAdapter
import com.khomichenko.sergey.homework1410.presentation.main_loan_screen.view_models.MainLoanFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main_loan.*
import javax.inject.Inject

class MainLoanFragment : Fragment() {

    private var _binding: FragmentMainLoanBinding? = null
    private val mBinding get() = _binding!!
    private var callback: OnBackPressedCallback? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainLoanFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainLoanBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
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
            ViewModelProvider(this, viewModelFactory)[MainLoanFragmentViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllLoans()
        floatingButton()
        addDataRecycler()
        exceptionHandling()
        showProgressBar()

        viewModel.allLoans.observe(this) {
            it.forEach { loanEntity ->
                if (loanEntity.state == "APPROVED") {
                    WorkManager.getInstance(requireContext())
                        .enqueue(viewModel.initializeWorker(loanEntity))
                }
            }
        }
    }

    private fun exceptionHandling() {
        viewModel.exception.observe(this) { exception ->
            Toast.makeText(context, exception, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar() {
        viewModel.loading.observe(this) { loading ->
            if (loading) {
                mBinding.mainProgressBar.visibility = View.VISIBLE
            } else {
                mBinding.mainProgressBar.visibility = View.GONE
            }
        }
    }

    private fun addDataRecycler() {
        mBinding.loanRecyclerView.apply {
            val loanAdapter = MainLoanAdapter(
                onViewClickListener = { loan ->
                    val idBundle = Bundle()
                    idBundle.putSerializable("id_loan", loan)
                    navigation().navigate(R.id.action_mainLoanFragment_to_loanInformationFragment,
                        idBundle)
                }
            )
            viewModel.allLoans.observe(this@MainLoanFragment) {
                loanAdapter.loanList = it
            }
            adapter = loanAdapter
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
                navigation().navigate(R.id.action_mainLoanFragment_to_registrationFragment)
            }
            R.id.btn_change_theme -> {
                if (PreferencesProvider.preferences.getTheme() == AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    PreferencesProvider.preferences.setTheme(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    PreferencesProvider.preferences.setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun floatingButton() {
        mBinding.addLoanBtn.setOnClickListener {
            val navigation = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navigation.navigate(R.id.action_mainLoanFragment_to_addNewLoanFragment)
        }
    }

    private fun navigation(): NavController =
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
}