package com.khomichenko.sergey.homework1410.presentation.loan_information.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomichenko.sergey.homework1410.domain.entity.main_loan.LoanEntity
import com.khomichenko.sergey.homework1410.domain.usecase.GetLoanInformationUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoanInformationViewModel @Inject constructor(
    private val getLoanInformationUseCase: GetLoanInformationUseCase,
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainViewModel", "Failed to post", throwable)
    }

    //загрузка
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _loanInformation = MutableLiveData<LoanEntity>()
    val loanInformation: LiveData<LoanEntity> = _loanInformation

    private val _exception = MutableLiveData<String>()
    val exception: LiveData<String> = _exception

    fun getLoanInformation(id: Int) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            try {
                val response = getLoanInformationUseCase.invoke(id).execute()
                withContext(Dispatchers.Main) {
                    _loanInformation.value = response.body()?.toLoanEntity()
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