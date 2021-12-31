package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.domain.repository.SharedRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val repository: SharedRepository
) {

    operator fun invoke(token: String) =
        repository.saveToken(token)
}