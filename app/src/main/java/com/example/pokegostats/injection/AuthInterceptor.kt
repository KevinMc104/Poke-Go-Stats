package com.example.pokegostats.injection

import okhttp3.Interceptor
import okhttp3.Response

/**
 * This intercepts the call before it goes to the API Endpoint and adds any required Auth headers
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("x-rapidapi-host", "pokemon-go1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "1179c81d02mshce35badb04cdbe5p1f2053jsn371b1a7b02dd")
                .build()
        )
    }
}