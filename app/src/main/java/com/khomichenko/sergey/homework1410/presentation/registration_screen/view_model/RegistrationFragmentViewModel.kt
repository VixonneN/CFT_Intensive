package com.khomichenko.sergey.homework1410.presentation.registration_screen.view_model

import android.content.Context
import android.util.Log
import android.util.LogPrinter
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.khomichenko.sergey.homework1410.data.data_source.shared_preferences.PreferencesProvider
import com.khomichenko.sergey.homework1410.di.App
import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
import com.khomichenko.sergey.homework1410.domain.usecase.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RegistrationFragmentViewModel @Inject constructor(
    private val registerRequestUseCase: RegisterRequestUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val getUnitUserUseCase: GetUnitUserUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val setUnitUserUseCase: SetUnitUserUseCase,
    private val setThemeUseCase: SetThemeUseCase
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

    private val _userLogin = MutableLiveData<Boolean>()
    val userLogin: LiveData<Boolean> = _userLogin

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
                _resultName.value = registerRequestUseCase.invoke(dataRegisterBody).name
                _loading.value = false
                _finish.value = true
            }
        }
    }

    fun checkToken() {
        val token = getSavedTokenUseCase.invoke()
        val userLogin = getUnitUserUseCase.invoke()
        if (token.isNotEmpty() && userLogin){
            _userLogin.value = true
        } else if (token.isEmpty() && !userLogin) {
            _userLogin.value = false
        }
    }

    fun deleteUser() {
        setUnitUserUseCase.invoke(false)
        deleteTokenUseCase.invoke()
    }

    fun changeTheme() {
        val currentTheme = getThemeUseCase.invoke()
        if (currentTheme == AppCompatDelegate.MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            setThemeUseCase.invoke(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            setThemeUseCase.invoke(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}