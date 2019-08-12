package com.example.loginwithfacebookdemotv.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.loginwithfacebookdemotv.AppLevelConstants
import com.example.loginwithfacebookdemotv.repository.UserAuthenticationActivityRepository
import com.example.loginwithfacebookdemotv.responsebean.facebookuserprofileresponse.FacebookUserProfileResponse
import com.example.loginwithfacebookdemotv.responsebean.loginresponse.FacebookLoginResponse
import com.example.loginwithfacebookdemotv.responsebean.loginstatusresponse.LoginStatusResponse

class UserAuthenticationActivityViewModel(application: Application) : AndroidViewModel(application) {

    var context: Application = application

    fun getFacebookLogin(context: Context): MutableLiveData<FacebookLoginResponse> {
        return UserAuthenticationActivityRepository.getFacebookLogin(
            context,
            AppLevelConstants.FACEBOOK_ID_ACCESS_TOKEN,
            AppLevelConstants.SCOPE
        )
    }

    fun getFacebookLoginStatus(context: Context, code: String): MutableLiveData<LoginStatusResponse> {
        return UserAuthenticationActivityRepository.getFacebookLoginStatus(context, AppLevelConstants.FACEBOOK_ID_ACCESS_TOKEN, code)
    }

    fun getFacebookUserProfile(
        context: Context,
        fields: String,
        accessToken: String
    ): MutableLiveData<FacebookUserProfileResponse> {
        return UserAuthenticationActivityRepository.getFacebookUserProfile(context, fields, accessToken)
    }

}

