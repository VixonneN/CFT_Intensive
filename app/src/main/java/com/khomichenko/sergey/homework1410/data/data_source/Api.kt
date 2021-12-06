package com.khomichenko.sergey.homework1410.data.data_source

import com.khomichenko.sergey.homework1410.data.auth.data_source.AuthBody
import com.khomichenko.sergey.homework1410.data.auth.data_source.RegistrationResponse
import com.khomichenko.sergey.homework1410.data.main_loan.LoanDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @POST("login")
    fun logIn(@Body authBody: AuthBody): Call<String>

    @POST("registration")
    fun register(@Body authBody: AuthBody): Call<RegistrationResponse>

    @GET("loans/all")
    fun getAllLoans() : Call<List<LoanDTO>>
}