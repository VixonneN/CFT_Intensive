package com.khomichenko.sergey.homework1410.data.data_source.network

import com.khomichenko.sergey.homework1410.data.dto.AuthBody
import com.khomichenko.sergey.homework1410.data.dto.RegistrationResponse
import com.khomichenko.sergey.homework1410.data.dto.ConditionsDTO
import com.khomichenko.sergey.homework1410.data.dto.CreateLoanBody
import com.khomichenko.sergey.homework1410.data.dto.LoanDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @POST("login")
    suspend fun logIn(@Body authBody: AuthBody): String

    @POST("registration")
    suspend fun register(@Body authBody: AuthBody): RegistrationResponse

    @GET("loans/all")
    suspend fun getAllLoans() : List<LoanDTO>

    @POST("loans")
    suspend fun createLoan(@Body createLoanBody: CreateLoanBody) : LoanDTO

    @GET("loans/conditions")
    suspend fun getConditions() : ConditionsDTO

    @GET("loans/{id}")
    suspend fun getLoanInformation(@Path("id") id: Int) : LoanDTO
}