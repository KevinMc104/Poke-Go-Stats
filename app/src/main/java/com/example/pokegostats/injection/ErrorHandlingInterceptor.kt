package com.example.pokegostats.injection

import okhttp3.Interceptor
import okhttp3.Response
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import java.io.IOException

/**
 * This intercepts the call before it goes to the API Endpoint and adds any required Auth headers
 */
//class ErrorHandlingInterceptor : Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val response = chain.proceed(request)
//
//        // TODO Add the rest of the responses and call out to an activity to display the error
//        if (response.code() == 500) {
//            startActivity(
//                Intent(
//                    this@ErrorHandlingActivity,
//                    ServerIsBrokenActivity::class.java
//                )
//            )
//
//            return response
//        }
//
//        return response
//    }
//}