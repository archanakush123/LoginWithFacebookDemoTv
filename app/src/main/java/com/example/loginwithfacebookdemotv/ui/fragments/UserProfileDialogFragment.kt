package com.example.loginwithfacebookdemotv.ui.fragments

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginwithfacebookdemotv.AppLevelConstants
import com.example.loginwithfacebookdemotv.R
import com.example.loginwithfacebookdemotv.callbacks.UserAuthenticationCodeCallback
import com.example.loginwithfacebookdemotv.databinding.UserProfileDialogBinding
import com.example.loginwithfacebookdemotv.responsebean.facebookuserprofileresponse.FacebookUserProfileResponse
import com.squareup.picasso.Picasso

class UserProfileDialogFragment : DialogFragment(), View.OnClickListener {

    private lateinit var userProfileDialogBinding: UserProfileDialogBinding
    private var facebookUserProfileResponse: FacebookUserProfileResponse? = null
    private var mListener: UserAuthenticationCodeCallback? = null

    companion object {
        fun newInstance(): UserProfileDialogFragment {
            return UserProfileDialogFragment()
        }
    }

    override fun onStart() {

        if (dialog!=null) {

            dialog.window?.attributes.let { it }?.width = android.view.WindowManager.LayoutParams.MATCH_PARENT
            dialog.window?.attributes.let { it }?.height = android.view.WindowManager.LayoutParams.MATCH_PARENT
        }
        super.onStart()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as UserAuthenticationCodeCallback

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userProfileDialogBinding = DataBindingUtil.inflate(inflater, R.layout.user_profile_dialog, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bundle = arguments
        if (bundle != null) {
            facebookUserProfileResponse =
                bundle.getSerializable(AppLevelConstants.USER_PROFILE_KEY) as FacebookUserProfileResponse
        }
        return userProfileDialogBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userProfileDialogBinding.txtUserName.text = facebookUserProfileResponse?.name
        userProfileDialogBinding.btnContinue.setOnClickListener(this)
        Picasso.with(context)
            .load(facebookUserProfileResponse?.picture?.data?.url)
            .into(userProfileDialogBinding.imgUserProfilePic)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnContinue -> {
                dismiss()
            }
        }
    }
}