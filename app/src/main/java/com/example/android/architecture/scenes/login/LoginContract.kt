package com.example.android.architecture.scenes.login

import android.support.v7.app.AppCompatActivity
import com.example.android.architecture.models.User
import com.example.android.architecture.scenes.BasePresenter
import com.example.android.architecture.scenes.BaseView
import com.example.android.architecture.scenes.widget.FingerprintAuthenticationDialogFragment

interface LoginContract {

  interface Presenter : BasePresenter, FingerprintAuthenticationDialogFragment.Callback {

    fun userLogin(user: User)

    fun generateFingerprintAuthenticationDialogFragment(): FingerprintAuthenticationDialogFragment

  }

  interface View : BaseView<Presenter> {

    fun isEnableFingerprintLoginButton(enable: Boolean)

    fun showToast(message: String)

    fun <T : AppCompatActivity> navigateTo(activity: Class<T>)
  }
}