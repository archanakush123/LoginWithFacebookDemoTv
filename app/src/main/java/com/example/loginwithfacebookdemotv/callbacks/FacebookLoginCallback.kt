package com.example.loginwithfacebookdemotv.callbacks

import com.example.loginwithfacebookdemotv.responsebean.loginresponse.FacebookLoginResponse

interface FacebookLoginCallback {
    fun configurationSuccess(facebookLoginResponse: FacebookLoginResponse?)
    fun configurationFailure(message: String);
}