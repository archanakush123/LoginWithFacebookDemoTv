package com.example.loginwithfacebookdemotv.callbacks

import com.example.loginwithfacebookdemotv.responsebean.loginstatusresponse.LoginStatusResponse

interface FacebookLoginStatusCallback {
    fun configurationSuccess(loginStatusResponse: LoginStatusResponse?)
    fun configurationFailure(message: String);
}