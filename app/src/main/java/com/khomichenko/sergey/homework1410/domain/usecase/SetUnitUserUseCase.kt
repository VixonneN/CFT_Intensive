package com.khomichenko.sergey.homework1410.domain.usecase

import com.khomichenko.sergey.homework1410.domain.repository.SharedRepository
import javax.inject.Inject

class SetUnitUserUseCase @Inject constructor(
    private val repository: SharedRepository
) {

    operator fun invoke(init: Boolean) =
        repository.setUnitUser(init)
}