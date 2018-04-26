package com.example.android.architecture.scenes.login

import android.app.KeyguardManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.android.architecture.R
import com.example.android.architecture.models.User
import com.example.android.architecture.scenes.login.LoginContract.Presenter
import com.example.android.architecture.utils.ActivityUtils
import com.example.android.architecture.utils.showToastWithLongDuration

class LoginFragment : Fragment(), LoginContract.View {

  companion object {
    const val FINGERPRINT_AUTHENTICATION_DIALOG_FRAGMENT_TAG =
      "FingerprintAuthenticationDialogFragment"
  }

  override lateinit var presenter: Presenter

  private lateinit var usernameEditText: EditText
  private lateinit var passwordEditText: EditText
  private lateinit var loginButton: Button
  private lateinit var fingerprintButton: Button

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root: View = inflater.inflate(R.layout.login_fragment, container, false)

    usernameEditText = root.findViewById(R.id.et_login_username)
    passwordEditText = root.findViewById(R.id.et_login_password)

    loginButton = root.findViewById<Button>(R.id.btn_login_login)
        .apply {
          setOnClickListener(LoginButtonClickListener())
        }


    fingerprintButton = root.findViewById<Button>(R.id.btn_login_fingerprint)
        .apply {
          // FP.4 指紋認証ボタンのクリックイベントの実装
          setOnClickListener(FingerprintLoginButtonClickListener())
        }

    return root
  }

  override fun onResume() {
    super.onResume()

    presenter.start()
  }

  override fun isEnableFingerprintLoginButton(enable: Boolean) {
    fingerprintButton.visibility = if (enable) View.VISIBLE else View.INVISIBLE
  }

  override fun showToast(message: String) {
    showToastWithLongDuration(message)
  }

  override fun <T : AppCompatActivity> navigateTo(activity: Class<T>) {
    ActivityUtils.startActivity(cls = activity)
  }

  private inner class LoginButtonClickListener : View.OnClickListener {
    override fun onClick(v: View?) {
      val username = usernameEditText.text.toString()
      val password = passwordEditText.text.toString()
      if ("" == username || "" == password) {
        return
      }
      val user = User(username, password)
      presenter.userLogin(user)
    }
  }

  /**
   * 指紋認証ログインボタンを押すと、プレゼンターから、認証用DialogFragmentを生成して、
   * そのDialogFragmentを表示させる
   */
  private inner class FingerprintLoginButtonClickListener : View.OnClickListener {
    override fun onClick(v: View?) {
      presenter.generateFingerprintAuthenticationDialogFragment()
          .show(activity?.fragmentManager ?: ActivityUtils.topActivity?.fragmentManager, FINGERPRINT_AUTHENTICATION_DIALOG_FRAGMENT_TAG)
    }
  }
}