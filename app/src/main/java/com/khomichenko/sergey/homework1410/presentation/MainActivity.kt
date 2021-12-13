package com.khomichenko.sergey.homework1410.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.khomichenko.sergey.homework1410.R
import com.khomichenko.sergey.homework1410.data.auth.auth_token.PreferencesProvider
import com.khomichenko.sergey.homework1410.databinding.ActivityMainBinding
import com.khomichenko.sergey.homework1410.di.App

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(PreferencesProvider.preferences.getTheme())
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        title = getString(R.string.toolbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}