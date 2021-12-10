package com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomichenko.sergey.homework1410.data.auth.auth_token.PreferencesProvider
import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
import com.khomichenko.sergey.homework1410.domain.usecase.LoginRequestUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthFragmentViewModel @Inject constructor(
    private val loginRequestUseCase: LoginRequestUseCase,
) : ViewModel() {

    //загрузка
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _finish = MutableLiveData<Boolean>()
    val finish: LiveData<Boolean> = _finish

    private val _exception = MutableLiveData<String>()
    val exception: LiveData<String> = _exception

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainViewModel", "Failed to post", throwable)
    }

    fun login(login: String, password: String) {
        if (login == "" || password == "") {
            _exception.value = " Введите корректные значения"
        } else {
            val dataRegisterBody = AuthEntity(login, password)
            _loading.value = true
            _finish.value = false
            viewModelScope.launch(handler + Dispatchers.IO) {
                try {
                    val response = loginRequestUseCase.invoke(dataRegisterBody).execute()
                    withContext(Dispatchers.Main) {
                        if (response.code() == 200 || response.code() == 201) {
                            val token = response.body()
                            if (token != null) {
                                PreferencesProvider.preferences.saveToken(token)
                                PreferencesProvider.preferences.setInitUser(true)
                            }
                            _loading.value = false
                            _finish.value = true
                        } else if (response.code() == 404) {
                            _exception.value = "Пользователь не найден"
                            _loading.value = false
                        } else if (response.code() == 403) {
                            _exception.value = "Что-то пошло не так, попробуйте позже"
                        }
                    }

                } catch (e: IOException) {
                    withContext(Dispatchers.Main) {
                        _exception.value = "Произошла какая-то ошибка, попробуйте ещё раз"
                        _loading.value = false
                    }

                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        _exception.value = "Ошибка соединения, попробуйте позже"
                        _loading.value = false
                    }
                }
            }
        }
    }
}