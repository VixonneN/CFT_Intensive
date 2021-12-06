package com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomichenko.sergey.homework1410.data.auth.auth_token.PreferencesProvider
import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
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

    //Имя, указанное при регистрации
    private val _resultName = MutableLiveData<String>()
    val resultName: LiveData<String> = _resultName

    //загрузка
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _finish = MutableLiveData<Boolean>()
    val finish: LiveData<Boolean> = _finish

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainViewModel", "Failed to post", throwable)
    }

    fun register(login: String, password: String) {
        val dataRegisterBody = AuthEntity(login, password)
        _loading.value = true
        _finish.value = false
        viewModelScope.launch(handler + Dispatchers.IO) {
            try {
                val response = registerRequestUseCase.invoke(dataRegisterBody).execute()
                withContext(Dispatchers.Main) {
                    val name = response.body()?.name
                    _resultName.value = name
                    _loading.value = false
                    _finish.value = true
                }
            } catch (e: IOException) {
                Log.e("TAG", "register: $e", IOException())
                _loading.value = false
            } catch (e: HttpException) {
                Log.e("TAG", "register: $e")
                _loading.value = false
            }
        }
    }

    fun login(login: String, password: String) {
        val dataRegisterBody = AuthEntity(login, password)
        _loading.value = true
        _finish.value = false
        viewModelScope.launch(handler + Dispatchers.IO) {
            try {
                val response = loginRequestUseCase.invoke(dataRegisterBody).execute()
                withContext(Dispatchers.Main) {
                    val token = response.body()
                    if (token != null) {
                        PreferencesProvider.preferences.saveToken(token)
                    }
                    _loading.value = false
                    _finish.value = true
                }
            } catch (e: IOException) {
                Log.e("TAG", "register: $e", IOException())
                _loading.value = false
            } catch (e: HttpException) {
                Log.e("TAG", "register: $e")
                _loading.value = false
            }
        }
    }
}