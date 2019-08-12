package com.example.loginwithfacebookdemotv.callbacks

import com.example.loginwithfacebookdemotv.responsebean.facebookuserprofileresponse.FacebookUserProfileResponse

interface FacebookUserProfileCallback {
    fun configurationSuccess(facebookUserProfileResponse: FacebookUserProfileResponse?)
    fun configurationFailure(message: String);
}