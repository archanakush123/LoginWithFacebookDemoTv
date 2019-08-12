package com.example.loginwithfacebookdemotv.ui.fragments

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.DialogFragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginwithfacebookdemotv.AppLevelConstants
import com.example.loginwithfacebookdemotv.R
import com.example.loginwithfacebookdemotv.callbacks.UserAuthenticationCodeCallback
import com.example.loginwithfacebookdemotv.databinding.AuthenticationCodeDialogBinding
import com.example.loginwithfacebookdemotv.responsebean.loginresponse.FacebookLoginResponse
import java.util.*
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.widget.FrameLayout


class AuthenticationCodeDialogFragment : DialogFragment() {


    private lateinit var authenticationCodeDialogBinding: AuthenticationCodeDialogBinding

    private var mContext: Context? = null
    private var facebookLoginResponse: FacebookLoginResponse? = null
    private var mListener: UserAuthenticationCodeCallback? = null

    private var mCountDownTimer: CountDownTimer? = null
    private var mTimeLeftInMillis: Long = 0
    private var mInterval: Int? = 5

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as UserAuthenticationCodeCallback

    }

    override fun onStart() {
        if (dialog!=null) {

            dialog.window?.attributes.let { it }?.width = android.view.WindowManager.LayoutParams.MATCH_PARENT
            dialog.window?.attributes.let { it }?.height = android.view.WindowManager.LayoutParams.MATCH_PARENT
        }
        super.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        authenticationCodeDialogBinding =
            DataBindingUtil.inflate(inflater, R.layout.authentication_code_dialog, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);
        val bundle = arguments
        if (bundle != null) {
            facebookLoginResponse = bundle.getSerializable(AppLevelConstants.LOGIN_RESPONSE_KEY) as FacebookLoginResponse
        }
        return authenticationCodeDialogBinding.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val first = getString(R.string.next_visit)
        val next = "<font color='#4080ff'>facebook.com/device</font>"
        authenticationCodeDialogBinding.txtNextVisit.text = (Html.fromHtml("$first $next"))
        authenticationCodeDialogBinding.txtUserCode.text = facebookLoginResponse?.userCode

        mTimeLeftInMillis = facebookLoginResponse?.expiresIn?.times(1000)?.toLong() ?: mTimeLeftInMillis
        mInterval = facebookLoginResponse?.interval ?: mInterval
        startTimer()
        authenticationCodeDialogBinding.btnCancel.requestFocus()
        authenticationCodeDialogBinding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun startTimer() {

        var intervalCount: Int = 0

        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {


            override fun onTick(millisUntilFinished: Long) {

                mTimeLeftInMillis = millisUntilFinished
                intervalCount++

                if (intervalCount == mInterval) {
                    facebookLoginResponse?.code?.let { mListener?.userAuthenticationCode(it) }
                    intervalCount = 0
                }
                updateCountDownText()
            }

            override fun onFinish() {
                dismiss()
            }
        }.start()
    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000) / 60
        val seconds = (mTimeLeftInMillis / 1000) % 60
        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        authenticationCodeDialogBinding.txtCodeExpireIn.text = timeLeftFormatted
    }


    companion object {

        fun newInstance(): AuthenticationCodeDialogFragment {

            return AuthenticationCodeDialogFragment()
        }
    }

    override fun dismiss() {
        if (mCountDownTimer != null) {
            mCountDownTimer?.cancel()
        }
        super.dismiss()
    }
}