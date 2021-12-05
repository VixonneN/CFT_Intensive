package com.khomichenko.sergey.homework1410.data.auth.data_source

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("login")
    fun logIn(@Body authBody: AuthBody): Call<String>

    //возвращается объект
    @POST("registration")
    fun register(@Body authBody: AuthBody): Call<RegistrationResponse>
}