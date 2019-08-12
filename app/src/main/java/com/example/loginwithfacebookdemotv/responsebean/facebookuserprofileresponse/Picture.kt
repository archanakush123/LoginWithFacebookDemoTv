package com.example.loginwithfacebookdemotv.responsebean.facebookuserprofileresponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Picture(

    @field:SerializedName("data")
    val data: Data? = null
) : Serializable