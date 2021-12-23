package com.khomichenko.sergey.homework1410.presentation.auth_screen.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.data.data_source.shared_preferences.PreferencesProvider
import com.khomichenko.sergey.homework1410.databinding.FragmentRegistrationBinding
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models.RegistrationFragmentViewModel
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val mBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RegistrationFragmentViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this,
                viewModelFactory)[RegistrationFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegistrationBinding
            .inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        if (PreferencesProvider.preferences.getInitUser() &&
            PreferencesProvider.preferences.savedToken.isNotEmpty()) {

            navigation().navigate(R.id.action_registrationFragment_to_mainLoanFragment)
        } else {
            deleteData()
            sendData()
            loading()
            navigate()
            exceptionHandling()
        }
    }

    private fun deleteData() {
        PreferencesProvider.preferences.deleteToken()
        PreferencesProvider.preferences.setInitUser(false)
    }

    private fun sendData() {
        mBinding.registrateBtnReg.setOnClickListener {
            val login = mBinding.loginEt.text.toString()
            val password = mBinding.passwordEt.text.toString()
            viewModel.register(login, password)
        }
    }

    private fun loading() {
        viewModel.loading.observe(this) { startLoading ->
            if (startLoading) {
                mBinding.authProgressBar.visibility = View.VISIBLE
                mBinding.registrateBtnReg.isEnabled = false
                mBinding.loginBtnReg.isEnabled = false
            } else {
                mBinding.authProgressBar.visibility = View.GONE
                mBinding.registrateBtnReg.isEnabled = true
                mBinding.loginBtnReg.isEnabled = true
            }
        }
    }

    private fun exceptionHandling() {
        viewModel.exception.observe(this) { exception ->
            Toast.makeText(context, exception, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigate() {
        mBinding.loginBtnReg.setOnClickListener {
            navigation().navigate(R.id.action_registrationFragment_to_authFragment)
        }
        viewModel.finish.observe(this) { finished ->
            if (finished) {
                val bundle = Bundle()
                viewModel.resultName.observe(this) { name ->
                    bundle.putString("result_name", name)
                }
                navigation().navigate(R.id.action_registrationFragment_to_authFragment, bundle)
            }
        }
    }

    private fun navigation(): NavController =
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.change_theme, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_theme_btn -> {
                viewModel.changeTheme(PreferencesProvider.preferences.getTheme())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}