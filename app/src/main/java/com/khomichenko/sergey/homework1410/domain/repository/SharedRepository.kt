package com.khomichenko.sergey.homework1410.domain.repository

interface SharedRepository {

    fun saveToken(token: String)
    fun getSavedToken() : String
    fun deleteToken()

    fun setUnitUser(init: Boolean)
    fun getUnitUser() : Boolean

    fun setTheme(theme: Int)
    fun getTheme() : Int
}