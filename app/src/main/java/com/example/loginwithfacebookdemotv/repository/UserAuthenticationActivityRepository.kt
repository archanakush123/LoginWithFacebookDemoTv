package com.example.loginwithfacebookdemotv.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.loginwithfacebookdemotv.callbacks.FacebookLoginCallback
import com.example.loginwithfacebookdemotv.callbacks.FacebookLoginStatusCallback
import com.example.loginwithfacebookdemotv.callbacks.FacebookUserProfileCallback
import com.example.loginwithfacebookdemotv.manager.DataRepository
import com.example.loginwithfacebookdemotv.responsebean.facebookuserprofileresponse.FacebookUserProfileResponse
import com.example.loginwithfacebookdemotv.responsebean.loginresponse.FacebookLoginResponse
import com.example.loginwithfacebookdemotv.responsebean.loginstatusresponse.LoginStatusResponse


object UserAuthenticationActivityRepository {

    private val TAG = UserAuthenticationActivityRepository::class.java.simpleName

    fun getFacebookLogin(context: Context, accessToken: String, scope: String): MutableLiveData<FacebookLoginResponse> {
        Log.d(TAG, " getFacebookLogin")
        val connection = MutableLiveData<FacebookLoginResponse>()
        DataRepository.instance.getFacebookLogin(accessToken, scope, object : FacebookLoginCallback {
            override fun configurationSuccess(facebookLoginResponse: FacebookLoginResponse?) {
                connection.postValue(facebookLoginResponse)
            }

            override fun configurationFailure(message: String) {
            }
        })
        return connection
    }

    fun getFacebookLoginStatus(
        context: Context,
        accessToken: String,
        code: String
    ): MutableLiveData<LoginStatusResponse> {
        Log.d(TAG, " getFacebookLoginStatus")
        val connection = MutableLiveData<LoginStatusResponse>()
        DataRepository.instance.getFacebookLoginStatus(accessToken, code, object : FacebookLoginStatusCallback {
            override fun configurationSuccess(loginStatusResponse: LoginStatusResponse?) {
                connection.postValue(loginStatusResponse)
            }

            override fun configurationFailure(message: String) {
            }
        })
        return connection
    }

    fun getFacebookUserProfile(
        context: Context,
        fields: String,
        accessToken: String
    ): MutableLiveData<FacebookUserProfileResponse> {
        Log.d(TAG, " getFacebookUserProfile")
        val connection = MutableLiveData<FacebookUserProfileResponse>()
        DataRepository.instance.getFacebookUserProfile(fields, accessToken, object : FacebookUserProfileCallback {
            override fun configurationSuccess(facebookUserProfileResponse: FacebookUserProfileResponse?) {
                connection.postValue(facebookUserProfileResponse)
            }

            override fun configurationFailure(message: String) {
            }
        })
        return connection
    }

}