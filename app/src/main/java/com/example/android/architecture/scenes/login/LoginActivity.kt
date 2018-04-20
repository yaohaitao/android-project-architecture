package com.example.android.architecture.scenes.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.architecture.R
import com.example.android.architecture.utils.ActivityUtils
import com.example.android.architecture.utils.ServiceProvider

class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.base_activity)

    var loginFragment =
      super.getSupportFragmentManager().findFragmentById(R.id.fl_base_fragment) as LoginFragment?

    if (loginFragment == null) {
      loginFragment = LoginFragment()
      ActivityUtils.addFragment(
          fragmentManager = super.getSupportFragmentManager(),
          fragment = loginFragment,
          containerId = R.id.fl_base_fragment
      )
    }

    LoginPresenter(service = ServiceProvider.makeUserService(), view = loginFragment)
  }
}