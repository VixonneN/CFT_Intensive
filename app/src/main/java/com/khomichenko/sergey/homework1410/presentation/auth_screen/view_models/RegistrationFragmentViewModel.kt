package com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomichenko.sergey.homework1410.domain.entity.AuthEntity
import com.khomichenko.sergey.homework1410.domain.usecase.LoginRequestUseCase
import com.khomichenko.sergey.homework1410.domain.usecase.RegisterRequestUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegistrationFragmentViewModel @Inject constructor(
    private val registerRequestUseCase: RegisterRequestUseCase,
    private val loginRequestUseCase: LoginRequestUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainViewModel", "Failed to post", throwable)
    }

    fun register(login: String, password: String) {
        val dataRegisterBody = AuthEntity(login, password)

        viewModelScope.launch(handler + Dispatchers.IO) {
            try {
                val response = registerRequestUseCase.invoke(dataRegisterBody).execute()
                withContext(Dispatchers.Main) {
                    val string = response.body().toString()
                    Log.d("TokenRequest", "register: $string")
                }
            } catch (e: IOException) {
                Log.e("TAG", "register: $e", IOException())
            } catch (e: HttpException) {
                Log.e("TAG", "register: $e")
            }
        }
    }

    fun login(login: String, password: String) {
        val dataRegisterBody = AuthEntity(login, password)

        viewModelScope.launch(handler + Dispatchers.IO) {
            try {
                val response = loginRequestUseCase.invoke(dataRegisterBody).execute()
                withContext(Dispatchers.Main) {
                    val string = response.body()
                    Log.d("TokenRequest", "login: $string")
                }
            } catch (e: IOException) {
                Log.e("TAG", "register: $e", IOException())
            } catch (e: HttpException) {
                Log.e("TAG", "register: $e")
            }
        }
    }
}