package com.khomichenko.sergey.homework1410.presentation.loan_information.view_model

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomichenko.sergey.homework1410.data.data_source.shared_preferences.PreferencesProvider
import com.khomichenko.sergey.homework1410.domain.usecase.GetLoanInformationUseCase
import com.khomichenko.sergey.homework1410.presentation.convecter.Converter
import com.khomichenko.sergey.homework1410.presentation.entity.LoanPresentation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class LoanInformationViewModel @Inject constructor(
    private val getLoanInformationUseCase: GetLoanInformationUseCase,
    private val converter: Converter
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

    private val _loanInformation = MutableLiveData<LoanPresentation>()
    val loanInformation: LiveData<LoanPresentation> = _loanInformation

    private val _exception = MutableLiveData<String>()
    val exception: LiveData<String> = _exception

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLoanInformation(id: Int) {
        _loading.value = true
        viewModelScope.launch(handler) {
            val response = getLoanInformationUseCase.invoke(id)
            _loanInformation.value = converter.convertToPresentation(response)
            Log.d(TAG, "getLoanInformation: ${response.state}")
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

    fun currentLoan(loansPresentation: LoanPresentation) {
        _loanInformation.value = loansPresentation
    }

    fun finishFragment() {
        _loading.value = false
    }
}