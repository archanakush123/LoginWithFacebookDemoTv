package com.example.loginwithfacebookdemotv.responsebean.facebookuserprofileresponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FacebookUserProfileResponse(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("error")
    val error: Error? = null,

    @field:SerializedName("picture")
    val picture: Picture? = null
) : Serializable