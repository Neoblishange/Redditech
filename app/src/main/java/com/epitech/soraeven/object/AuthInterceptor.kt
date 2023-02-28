package com.epitech.soraeven.`object`;

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authToken: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authenticatedRequest = originalRequest.newBuilder()
        .header("Authorization", "Bearer $authToken")
        .build()
        return chain.proceed(authenticatedRequest)
        }
        }
