/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.example.android.architecture.scenes.widget

import android.annotation.TargetApi
import android.app.DialogFragment
import android.hardware.fingerprint.FingerprintManager
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.android.architecture.R
import com.example.android.architecture.utils.FingerprintUiHelper

@TargetApi(VERSION_CODES.M)
/**
 * A dialog which uses fingerprint APIs to authenticate the user, and falls back to password
 * authentication if fingerprint is not available.
 */
class FingerprintAuthenticationDialogFragment : DialogFragment(),
    FingerprintUiHelper.Callback {

  private lateinit var cancelButton: Button

  private lateinit var callback: Callback
  private lateinit var cryptoObject: FingerprintManager.CryptoObject
  private lateinit var fingerprintUiHelper: FingerprintUiHelper

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Do not create a new Fragment when the Activity is re-created such as orientation changes.
    retainInstance = true
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    dialog.setTitle("Fingerprint Authentication")
    return inflater.inflate(R.layout.fingerprint_dialog, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    cancelButton = view.findViewById<Button>(R.id.cancel_button).also {
      it.setOnClickListener{
        this.dismiss()
      }
    }

    fingerprintUiHelper = FingerprintUiHelper(
        activity.getSystemService(FingerprintManager::class.java),
        view.findViewById(R.id.fingerprint_icon),
        view.findViewById(R.id.fingerprint_status),
        this
    )
  }

  override fun onResume() {
    super.onResume()
    // 指紋認証のリスニングを始める
    fingerprintUiHelper.startListening(cryptoObject)
  }

  override fun onPause() {
    super.onPause()
    // 指紋認証のリスニングを止まる
    fingerprintUiHelper.stopListening()
  }

  fun setCallback(callback: Callback) {
    this.callback = callback
  }

  fun setCryptoObject(cryptoObject: FingerprintManager.CryptoObject) {
    this.cryptoObject = cryptoObject
  }

  override fun onAuthenticated() {
    // 認証を成功したら、LoginPresenterのsuccess()方法を呼び出す
    callback.success()
    // DialogFragmentを消させる
    dismiss()
  }

  override fun onError() {
    dismiss()
  }

  interface Callback {
    fun success()

    fun createKey(
      keyName: String
    )
  }
}
