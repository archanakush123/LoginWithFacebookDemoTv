package com.example.loginwithfacebookdemotv.manager

import android.util.Log
import com.example.loginwithfacebookdemotv.callbacks.FacebookLoginCallback
import com.example.loginwithfacebookdemotv.callbacks.FacebookLoginStatusCallback
import com.example.loginwithfacebookdemotv.callbacks.FacebookUserProfileCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DataRepository {

    private val TAG = DataRepository::class.java.simpleName

    companion object {

        private var mInstance: DataRepository? = null

        val instance: DataRepository
            @Synchronized get() {
                if (mInstance == null) {
                    mInstance = DataRepository()
                }
                return mInstance as DataRepository
            }
    }

    fun getFacebookLogin(accessToken: String, scope: String, callback: FacebookLoginCallback) {
        Log.d(TAG, " getFacebookLogin")
        val mDisposable = CompositeDisposable()
        ApiManager.instance.getFacebookLogin(accessToken, scope)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ loginResponse ->
                if (loginResponse.error == null) {
                    callback.configurationSuccess(loginResponse)
                } else {
                    callback.configurationFailure(loginResponse?.error.message.toString())
                }
            }) { e ->
                Log.e(TAG, e.message)
                callback.configurationFailure(e.message.toString())
            }?.let { mDisposable.add(it) }
    }

    fun getFacebookLoginStatus(accessToken: String, code: String, callback: FacebookLoginStatusCallback) {
        Log.d(TAG, " getFacebookLoginStatus")
        val mDisposable = CompositeDisposable()
        ApiManager.instance.getFacebookLoginStatus(accessToken, code)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ loginStatusResponse ->
                if (loginStatusResponse.error == null) {
                    callback.configurationSuccess(loginStatusResponse)
                } else {
                    callback.configurationFailure(loginStatusResponse?.error.message.toString())
                }
            }) { e ->
                Log.e(TAG, e.message)
                callback.configurationFailure(e.message.toString())
            }?.let { mDisposable.add(it) }
    }

    fun getFacebookUserProfile(fields: String, accessToken: String, callback: FacebookUserProfileCallback) {
        Log.d(TAG, " getFacebookUserProfile")
        val mDisposable = CompositeDisposable()
        ApiManager.instance.getFacebookUserProfile(fields, accessToken)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ facebookUserProfileResponse ->
                if (facebookUserProfileResponse.error == null) {
                    callback.configurationSuccess(facebookUserProfileResponse)
                } else {
                    callback.configurationFailure(facebookUserProfileResponse?.error.message.toString())
                }
            }) { e ->
                Log.e(TAG, e.message)
                callback.configurationFailure(e.message.toString())
            }?.let { mDisposable.add(it) }
    }


}

