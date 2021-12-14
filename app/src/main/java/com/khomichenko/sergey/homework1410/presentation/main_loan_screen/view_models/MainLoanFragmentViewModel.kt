package com.khomichenko.sergey.homework1410.presentation.main_loan_screen.view_models

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import com.khomichenko.sergey.homework1410.data.shared_preferences.PreferencesProvider
import com.khomichenko.sergey.homework1410.domain.worker.NotificationWorker
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.usecase.GetAllLoansUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainLoanFragmentViewModel @Inject constructor(
    private val getAllLoansUseCase: GetAllLoansUseCase,
) : ViewModel() {

    //загрузка
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _allLoans = MutableLiveData<List<LoanEntity>>()
    val allLoans: LiveData<List<LoanEntity>> = _allLoans

    private val _exception = MutableLiveData<String>()
    val exception: LiveData<String> = _exception

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainViewModel", "Failed to post", throwable)
    }

    fun getAllLoans() {
        _loading.value = true
        viewModelScope.launch(handler + Dispatchers.IO) {
            try {
                val request = getAllLoansUseCase.invoke().execute()
                withContext(Dispatchers.Main) {
                    if (request.code() == 200 || request.code() == 201) {
                        _allLoans.value = request.body()?.map { it.toLoanEntity() }
                        _loading.value = false
                    } else if (request.code() == 401) {
                        _exception.value = "Не удалось проверить авторизацию. Авторизируйтесь ещё раз"
                        _loading.value = false
                    } else if (request.code() == 403) {
                        _exception.value = "Доступ запрещён"
                        _loading.value = false
                    } else if (request.code() == 404) {
                        _exception.value = "Произошла какая-то ошибка, попробуйте ещё раз"
                        _loading.value = false
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    _exception.value = "Произошла какая-то ошибка, попробуйте ещё раз"
                    _loading.value = false
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    _exception.value = "Произошла какая-то ошибка, попробуйте ещё раз"
                    _loading.value = false
                }
            }
        }
    }

    fun initializeWorker(loanEntity: LoanEntity) : PeriodicWorkRequest {
        val data = Data.Builder()
            .putString("fio_loan", loanEntity.lastName + " " + loanEntity.firstName)
            .putInt("amount_loan", loanEntity.amount.toInt())
            .build()
        return PeriodicWorkRequest.Builder(NotificationWorker::class.java,
                25, TimeUnit.MINUTES)
            .setInputData(data)
            .build()
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

    fun finishFragment(){
        _loading.value = false
    }
}