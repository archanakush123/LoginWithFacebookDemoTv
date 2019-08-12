package com.example.loginwithfacebookdemotv.manager

import android.util.Log
import com.example.loginwithfacebookdemotv.ApiInterface
import com.example.loginwithfacebookdemotv.AppLevelConstants
import com.example.loginwithfacebookdemotv.networking.ApiInterceptor
import com.example.loginwithfacebookdemotv.responsebean.facebookuserprofileresponse.FacebookUserProfileResponse
import com.example.loginwithfacebookdemotv.responsebean.loginresponse.FacebookLoginResponse
import com.example.loginwithfacebookdemotv.responsebean.loginstatusresponse.LoginStatusResponse
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiManager private constructor() {

    private val TAG = ApiManager::class.java.simpleName

    private var mApiInterface: ApiInterface? = null


    init {
        createNontonApiClient()
    }

    private fun createNontonApiClient() {
        Log.d(TAG, "createNontonApiClient")
        mApiInterface = getRetrofitClient().create(ApiInterface::class.java)
    }

    private fun getRetrofitClient(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(AppLevelConstants.FACEBOOK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getHttpClient())
            .build()
    }

    private fun getHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.NONE
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(ApiInterceptor())
        httpClient.addInterceptor(interceptor)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }

    companion object {
        private var mInstance: ApiManager? = null

        val instance: ApiManager
            @Synchronized get() {
                if (mInstance == null) {
                    mInstance = ApiManager()
                }
                return mInstance as ApiManager
            }
    }

    fun getFacebookLogin(accessToken: String, scope: String): Observable<FacebookLoginResponse>? {
        Log.d(TAG, " getFacebookLogin")
        return mApiInterface?.getFacebookLogin(accessToken, scope)
    }

    fun getFacebookLoginStatus(accessToken: String, code: String): Observable<LoginStatusResponse>? {
        Log.d(TAG, " getFacebookLoginStstus")
        return mApiInterface?.getFacebookLoginStatus(accessToken, code)
    }

    fun getFacebookUserProfile(fields: String, accessToken: String): Observable<FacebookUserProfileResponse>? {
        Log.d(TAG, " getFacebookUserProfile")
        return mApiInterface?.getFacebookUserProfile(fields, accessToken)
    }


}
