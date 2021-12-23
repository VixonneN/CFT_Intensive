package com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomichenko.sergey.homework1410.data.data_source.shared_preferences.PreferencesProvider
import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
import com.khomichenko.sergey.homework1410.domain.usecase.LoginRequestUseCase
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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
        when (throwable) {
            is UnknownHostException -> Log.e("MainViewModel", "Failed to post", throwable)
            is SocketTimeoutException -> Log.e("MainViewModel", "Failed to post", throwable)
            is HttpException -> {
                when (throwable.code()) {
                    401 ->
                        _exception.value =
                            "Не удалось проверить авторизацию. Авторизируйтесь ещё раз"
                    403 -> _exception.value = "Доступ запрещён"

                    404 -> _exception.value = "Ничего не найдено, попробуйте ещё раз"
                }
            }
        }
    }

    fun login(login: String, password: String) {
        if (login == "" || password == "") {
            _exception.value = " Введите корректные значения"
        } else {
            val dataRegisterBody = AuthEntity(login, password)
            _loading.value = true
            _finish.value = false
            viewModelScope.launch(handler + Dispatchers.IO) {
                val response = loginRequestUseCase.invoke(dataRegisterBody)
                withContext(Dispatchers.Main) {
                    val token = async { response }
                    PreferencesProvider.preferences.saveToken(token.await())
                    PreferencesProvider.preferences.setInitUser(true)
                    _finish.value = true
                    _loading.value = false
                }

            }
        }
    }

    fun changeTheme(currentTheme: Int) {
        if (currentTheme == AppCompatDelegate.MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            PreferencesProvider.preferences.setTheme(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            PreferencesProvider.preferences.setTheme(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun finishFragment() {
        _finish.value = false
        _loading.value = false
    }
}