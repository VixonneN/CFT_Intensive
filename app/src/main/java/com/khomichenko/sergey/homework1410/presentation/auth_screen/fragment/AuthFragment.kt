package com.khomichenko.sergey.homework1410.presentation.auth_screen.fragment

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
import androidx.navigation.fragment.findNavController
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.data.auth.auth_token.PreferencesProvider
import com.khomichenko.sergey.homework1410.databinding.FragmentAuthBinding
import com.khomichenko.sergey.homework1410.databinding.FragmentRegistrationBinding
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models.AuthFragmentViewModel
import com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models.RegistrationFragmentViewModel
import javax.inject.Inject

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val mBinding get() = _binding!!
    private var callback: OnBackPressedCallback? = null

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
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigation().navigate(R.id.action_authFragment_to_registrationFragment)
                viewModel.finishFragment()
            }
        }.also {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, it)
        }
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        getText()
        getUserName()
        loading()
        navigate()
        exceptionHandling()
    }

    private fun getUserName() {
        mBinding.loginEt.setText(registeredName)
    }

    private fun exceptionHandling(){
        viewModel.exception.observe(this) { exception ->
            Toast.makeText(context, exception, Toast.LENGTH_SHORT).show()
        }
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
        viewModel.finish.observe(this) { finished ->
            if (finished) {
                navigation().navigate(R.id.action_authFragment_to_mainLoanFragment)
            }
        }
        viewModel.finishFragment()
    }

    private fun navigation() : NavController =
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.change_theme, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_theme_btn -> {
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
}