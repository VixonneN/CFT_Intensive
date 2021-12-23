package com.khomichenko.sergey.homework1410.presentation.add_new_loan_screen.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.ConditionsEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.CreateLoanEntity
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.usecase.CreateNewLoanUseCase
import com.khomichenko.sergey.homework1410.domain.usecase.GetConditionsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.io.PrintStream
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class AddNewLoanFragmentViewModel @Inject constructor(
    private val createNewLoanUseCase: CreateNewLoanUseCase,
    private val getConditionsUseCase: GetConditionsUseCase,
) : ViewModel() {

    private val _finished = MutableLiveData<Boolean>()
    val finished: LiveData<Boolean> = _finished

    //загрузка
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    //условия
    private val _conditions = MutableLiveData<ConditionsEntity>()
    val conditions: LiveData<ConditionsEntity> = _conditions

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

    private val _loan = MutableLiveData<LoanEntity>()
    val loan: LiveData<LoanEntity> = _loan

    fun addLoan(
        amount: Int,
        firstName: String,
        lastName: String,
        percent: Double,
        period: Int,
        number: String,
    ) {
        _loading.value = true
        val createLoanEntity =
            CreateLoanEntity(amount, firstName, lastName, percent, period, number)
        viewModelScope.launch(handler) {
            val response = createNewLoanUseCase.invoke(createLoanEntity)
            _loan.value = response
            _loading.value = false
            _finished.value = true
        }
    }

    fun getConditions() {
        _loading.value = true
        viewModelScope.launch(handler) {
            val response = getConditionsUseCase.invoke()
            _conditions.value = response
            _loading.value = false
        }
    }

    fun finishFragment() {
        _finished.value = false
        _loading.value = false
    }
}