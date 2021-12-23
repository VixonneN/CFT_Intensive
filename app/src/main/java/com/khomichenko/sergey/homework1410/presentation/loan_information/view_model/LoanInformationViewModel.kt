package com.khomichenko.sergey.homework1410.presentation.loan_information.view_model

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomichenko.sergey.homework1410.data.data_source.shared_preferences.PreferencesProvider
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.usecase.GetLoanInformationUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class LoanInformationViewModel @Inject constructor(
    private val getLoanInformationUseCase: GetLoanInformationUseCase,
) : ViewModel() {

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

    //загрузка
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _loanInformation = MutableLiveData<LoanEntity>()
    val loanInformation: LiveData<LoanEntity> = _loanInformation

    private val _exception = MutableLiveData<String>()
    val exception: LiveData<String> = _exception

    fun getLoanInformation(id: Int) {
        _loading.value = true
        viewModelScope.launch(handler) {
            val response = getLoanInformationUseCase.invoke(id)
            _loanInformation.value = response
            _loading.value = false
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

    fun currentLoan(loanEntity: LoanEntity) {
        _loanInformation.value = loanEntity
    }

    fun finishFragment() {
        _loading.value = false
    }

    fun changeState(state: String): String {
        return when (state) {
            "REJECTED" -> state.replace("REJECTED", "Отклонено")
            "REGISTERED" -> state.replace("REGISTERED", "Зарегистрировано")
            "APPROVED" -> state.replace("APPROVED", "Одобрено")
            else -> ""
        }
    }
}