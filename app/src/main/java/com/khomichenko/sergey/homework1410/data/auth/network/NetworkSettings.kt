package com.khomichenko.sergey.homework1410.data.auth.network

import com.khomichenko.sergey.homework1410.data.auth.auth_token.AuthInterceptor
import com.khomichenko.sergey.homework1410.data.auth.data_source.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkSettings {

    private companion object {
        const val BASE_URL = "http://focusstart.appspot.com/"
    }

    private val api: Api
    fun api(): Api {
        return api
    }

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor(AuthInterceptor())
        val retrofit: Retrofit = Retrofit.Builder()
            .client(builder.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)
    }
}