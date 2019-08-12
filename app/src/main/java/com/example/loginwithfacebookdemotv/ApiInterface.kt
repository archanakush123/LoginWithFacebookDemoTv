package com.example.loginwithfacebookdemotv

import com.example.loginwithfacebookdemotv.responsebean.facebookuserprofileresponse.FacebookUserProfileResponse
import com.example.loginwithfacebookdemotv.responsebean.loginresponse.FacebookLoginResponse
import com.example.loginwithfacebookdemotv.responsebean.loginstatusresponse.LoginStatusResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @Headers("x-platform: android")
    @POST("v2.6/device/login")
    fun getFacebookLogin(@Query("access_token") accessToken: String, @Query("scope") scope: String): Observable<FacebookLoginResponse>

    @Headers("x-platform: android")
    @POST("v2.6/device/login_status")
    fun getFacebookLoginStatus(@Query("access_token") accessToken: String, @Query("code") code: String): Observable<LoginStatusResponse>

    @Headers("x-platform: android")
    @GET("v2.3/me")
    fun getFacebookUserProfile(@Query("fields") fields: String, @Query("access_token") accessToken: String): Observable<FacebookUserProfileResponse>
}