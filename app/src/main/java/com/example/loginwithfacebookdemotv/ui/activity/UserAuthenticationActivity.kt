package com.example.loginwithfacebookdemotv.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.example.loginwithfacebookdemotv.AppLevelConstants
import com.example.loginwithfacebookdemotv.R
import com.example.loginwithfacebookdemotv.callbacks.UserAuthenticationCodeCallback
import com.example.loginwithfacebookdemotv.ui.fragments.AuthenticationCodeDialogFragment
import com.example.loginwithfacebookdemotv.ui.fragments.UserProfileDialogFragment
import com.example.loginwithfacebookdemotv.viewmodel.UserAuthenticationActivityViewModel
import java.io.Serializable


class UserAuthenticationActivity : FragmentActivity(), UserAuthenticationCodeCallback {

    private lateinit var userAuthenticationActivityViewModel: UserAuthenticationActivityViewModel
    private var authenticationCodeDialogFragment: AuthenticationCodeDialogFragment? = null
    private var userProfileDialogFragment: UserProfileDialogFragment? = null
    private var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userAuthenticationActivityViewModel = ViewModelProviders.of(this).get(UserAuthenticationActivityViewModel::class.java)
        callFacebookLoginApi()
    }

    private fun callFacebookLoginApi() {

        count++

        if(count == 3) {
            authenticationCodeDialogFragment?.dismiss()
            count = 0
        }
        userAuthenticationActivityViewModel.getFacebookLogin(this).observe(this, Observer {
            if (it != null) {
                Log.d("code", it.userCode)
                val bundle = Bundle()
                bundle.putSerializable(AppLevelConstants.LOGIN_RESPONSE_KEY, it as Serializable)
                authenticationCodeDialogFragment = AuthenticationCodeDialogFragment.newInstance()
                authenticationCodeDialogFragment?.arguments = bundle
                authenticationCodeDialogFragment?.isCancelable = false
                authenticationCodeDialogFragment?.show(supportFragmentManager, AppLevelConstants.TAG_FRAGMENT_ALERT)

                it.expiresIn?.times(1000)?.toLong()?.let { it1 ->
                    Handler().postDelayed(Runnable {
                        if(count < 3){
                            callFacebookLoginApi()
                        }
                    }, it1)
                }
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun userAuthenticationCode(code: String) {
        callLoginStatusApi(code)
    }

    private fun callLoginStatusApi(code: String?) {

        code?.let { it1 ->
            userAuthenticationActivityViewModel.getFacebookLoginStatus(this@UserAuthenticationActivity, it1)
                .observe(this@UserAuthenticationActivity, Observer {

                    if (it != null) {
                        if (authenticationCodeDialogFragment != null) {
                            authenticationCodeDialogFragment?.dismiss()
                        }
                        callUserProfileApi(it.accessToken)
                    } else {
                        Toast.makeText(this@UserAuthenticationActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun callUserProfileApi(accessToken: String?) {

        accessToken?.let { it1 ->
            userAuthenticationActivityViewModel.getFacebookUserProfile(this, AppLevelConstants.FACEBOOK_FIELD, it1)
                .observe(this, Observer {
                    if (it != null) {
                        val bundle = Bundle()
                        bundle.putSerializable(AppLevelConstants.USER_PROFILE_KEY, it as Serializable)
                        userProfileDialogFragment = UserProfileDialogFragment.newInstance()
                        userProfileDialogFragment?.arguments = bundle
                        userProfileDialogFragment?.isCancelable = false
                        userProfileDialogFragment?.show(supportFragmentManager, AppLevelConstants.TAG_FRAGMENT_ALERT)
                    }
                })
        }
    }


}
