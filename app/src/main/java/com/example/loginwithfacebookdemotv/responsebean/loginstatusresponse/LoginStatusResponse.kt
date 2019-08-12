package com.example.loginwithfacebookdemotv.responsebean.loginstatusresponse

import com.google.gson.annotations.SerializedName

data class LoginStatusResponse(

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("error")
    val error: Error? = null,

    @field:SerializedName("expires_in")
    val expiresIn: Int? = null,

    @field:SerializedName("data_access_expiration_time")
    val dataAccessExpirationTime: Int? = null
)