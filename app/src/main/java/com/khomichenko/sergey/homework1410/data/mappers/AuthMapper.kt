package com.khomichenko.sergey.homework1410.data.mappers

import com.khomichenko.sergey.homework1410.data.auth.data_source.AuthBody
import com.khomichenko.sergey.homework1410.domain.entity.AuthEntity

class AuthMapper {
    companion object {
        fun toAuthEntity(authBody: AuthBody) : AuthEntity {
            return AuthEntity(authBody.name, authBody.password)
        }

        fun toAuthBody(authEntity: AuthEntity): AuthBody{
            return AuthBody(authEntity.name, authEntity.password)
        }
    }
}