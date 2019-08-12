package com.example.loginwithfacebookdemotv.networking

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiInterceptor : Interceptor {

    private val TAG = ApiInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            Log.d(TAG, "request url : " + response.request().url())
            Log.d(TAG, "response code : " + response.code())
            return response
        } catch (e: IOException) {
            Log.d(TAG, "Error in execute api request : IO")
        } catch (ex: Exception) {
            Log.d(TAG, "Error in execute api " + ex.message)
        }
        return null
    }
}
