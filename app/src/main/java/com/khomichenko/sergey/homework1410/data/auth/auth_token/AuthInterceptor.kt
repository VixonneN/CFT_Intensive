package com.khomichenko.sergey.homework1410.data.auth.auth_token

import android.text.TextUtils
import com.khomichenko.sergey.homework1410.data.shared_preferences.PreferencesProvider
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val token = PreferencesProvider.preferences.savedToken
        if (!TextUtils.isEmpty(token)) {
            val authenticatedRequest = request.newBuilder()
                .header("Authorization", token).build()
            return chain.proceed(authenticatedRequest)
        }
        return chain.proceed(request)
    }
}