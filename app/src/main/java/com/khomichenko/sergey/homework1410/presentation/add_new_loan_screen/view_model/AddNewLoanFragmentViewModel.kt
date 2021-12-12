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
        Log.e("MainViewModel", "Failed to post", throwable)
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
        viewModelScope.launch(handler + Dispatchers.IO) {
            try {
                val response = createNewLoanUseCase.invoke(createLoanEntity).execute()
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 || response.code() == 201) {
                        _loan.value = response.body()?.toLoanEntity()
                        _loading.value = false
                        _finished.value = true
                    } else if (response.code() == 401) {
                        _exception.value =
                            "Не удалось проверить авторизацию. Авторизируйтесь ещё раз"
                        _loading.value = false
                    } else if (response.code() == 403) {
                        _exception.value = "Доступ запрещён"
                        _loading.value = false

                    } else if (response.code() == 404) {
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

    fun getConditions() {
        _loading.value = true
        viewModelScope.launch(handler + Dispatchers.IO) {
            try {
                val response = getConditionsUseCase.invoke().execute()
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 || response.code() == 201) {
                        _conditions.value = response.body()?.toEntity()
                        _loading.value = false
                    } else if (response.code() == 401) {
                        _exception.value =
                            "Не удалось проверить авторизацию. Авторизируйтесь ещё раз"
                        _loading.value = false

                    } else if (response.code() == 403) {
                        _exception.value = "Доступ запрещён"
                        _loading.value = false

                    } else if (response.code() == 404) {
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

    fun finishFragment() {
        _finished.value = false
        _loading.value = false
    }
}