package com.khomichenko.sergey.homework1410.presentation.auth_screen.view_models

import android.util.Log
import androidx.lifecycle.*
import com.khomichenko.sergey.homework1410.domain.entity.auth.AuthEntity
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
        Log.e("MainViewModel", "Failed to post", throwable)
    }

    fun register(login: String, password: String) {
        if (login == "" || password == "") {
            _exception.value = "Введите корректные значения"
        } else {
            val dataRegisterBody = AuthEntity(login, password)
            _loading.value = true
            _finish.value = false
            viewModelScope.launch(handler + Dispatchers.IO) {
                try {
                    val response = registerRequestUseCase.invoke(dataRegisterBody).execute()
                    withContext(Dispatchers.Main) {
                        if (response.code() == 200 || response.code() == 201) {
                            val name = response.body()?.name
                            _resultName.value = name
                            _loading.value = false
                            _finish.value = true
                        } else if (response.code() == 400) {
                            _exception.value = "Пользователь уже существует"
                            _loading.value = false
                        } else if (response.code() == 401) {
                            _exception.value =
                                "Не удалось проверить авторизацию. Авторизируйтесь ещё раз"
                            _loading.value = false
                        } else if (response.code() == 403) {
                            _exception.value = "Доступ запрещён"
                            _loading.value = false
                        } else if (response.code() == 404) {
                            _exception.value = "Ничего не найдено, попробуйте ещё раз"
                            _loading.value = false
                        }
                    }
                } catch (e: IOException) {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _exception.value = "Произошла какая-то ошибка, попробуйте ещё раз"
                    }

                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _exception.value = "Произошла ошибка сети, попробуйте ещё раз"
                    }
                }
            }
        }
    }
}