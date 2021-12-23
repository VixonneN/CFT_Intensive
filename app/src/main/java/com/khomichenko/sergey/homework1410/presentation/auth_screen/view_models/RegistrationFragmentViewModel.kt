package com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.*
import com.khomichenko.sergey.homework1410.data.data_source.shared_preferences.PreferencesProvider
import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
import com.khomichenko.sergey.homework1410.domain.usecase.RegisterRequestUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RegistrationFragmentViewModel @Inject constructor(
    private val registerRequestUseCase: RegisterRequestUseCase,
) : ViewModel() {

    //Имя, указанное при регистрации
    private val _resultName = MutableLiveData<String>()
    val resultName: LiveData<String> = _resultName

    //загрузка
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    //определение конца загрузки
    private val _finish = MutableLiveData<Boolean>()
    val finish: LiveData<Boolean> = _finish

    //выведение на экран ошибок
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

    fun register(login: String, password: String) {
        if (login == "" || password == "") {
            _exception.value = "Введите корректные значения"
        } else {
            val dataRegisterBody = AuthEntity(login, password)
            _loading.value = true
            _finish.value = false
            viewModelScope.launch(handler) {
                val response = registerRequestUseCase.invoke(dataRegisterBody)
                val name = response.name
                _resultName.value = name
                _loading.value = false
                _finish.value = true
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

}