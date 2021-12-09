package com.khomichenko.sergey.homework1410.presentation.auth_screen.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.databinding.FragmentAuthBinding
import com.khomichenko.sergey.homework1410.databinding.FragmentRegistrationBinding
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models.AuthFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models.RegistrationFragmentViewModel
import javax.inject.Inject

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val mBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AuthFragmentViewModel
    private var registeredName: String? = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[AuthFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAuthBinding.inflate(layoutInflater, container, false)
        registeredName = arguments?.getString("result_name")
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        getText()
        getUserName()
        loading()
        navigate()
    }

    private fun getUserName() {
        mBinding.loginEt.setText(registeredName)

    }

    private fun getText() {
        mBinding.loginBtnAuth.setOnClickListener {
            val login = mBinding.loginEt.text.toString()
            val password = mBinding.passwordEt.text.toString()
            viewModel.login(login, password)
        }
    }

    private fun loading() {
        viewModel.loading.observe(this) { startLoading ->
            if (startLoading) {
                mBinding.authProgressBar.visibility = View.VISIBLE
                mBinding.loginBtnAuth.isEnabled = false
            } else {
                mBinding.authProgressBar.visibility = View.GONE
                mBinding.loginBtnAuth.isEnabled = true
            }
        }
    }

    private fun navigate() {
        val navigation = findNavController()
        viewModel.finish.observe(this) { finished ->
            if (finished) {
                navigation.navigate(R.id.action_authFragment_to_mainLoanFragment)
            }
        }
    }
}