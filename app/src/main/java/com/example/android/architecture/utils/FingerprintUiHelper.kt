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

package com.example.android.architecture.utils

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.CancellationSignal
import android.widget.ImageView
import android.widget.TextView
import com.example.android.architecture.R

/**
 * Small helper class to manage text/icon around fingerprint authentication UI.
 */
@TargetApi(VERSION_CODES.M)
class FingerprintUiHelper
/**
 * Constructor for [FingerprintUiHelper].
 */
internal constructor(private val fingerprintMgr: FingerprintManager,
        private val icon: ImageView,
        private val errorTextView: TextView,
        private val callback: Callback
) : FingerprintManager.AuthenticationCallback() {

    private var cancellationSignal: CancellationSignal? = null
    private var selfCancelled = false

    val isFingerprintAuthAvailable: Boolean
        get() = fingerprintMgr.isHardwareDetected && fingerprintMgr.hasEnrolledFingerprints()

    private val resetErrorTextRunnable = Runnable {
        icon.setImageResource(R.drawable.ic_fp_40px)
        errorTextView.run {
            setTextColor(errorTextView.resources.getColor(R.color.hint_color, null))
            text = "Touch Sensor"
        }
    }

    fun startListening(cryptoObject: FingerprintManager.CryptoObject) {
        if (!isFingerprintAuthAvailable) return
        cancellationSignal = CancellationSignal()
        selfCancelled = false
        fingerprintMgr.authenticate(cryptoObject, cancellationSignal, 0, this, null)
        icon.setImageResource(R.drawable.ic_fp_40px)
    }

    fun stopListening() {
        cancellationSignal?.also {
            selfCancelled = true
            it.cancel()
        }
        cancellationSignal = null
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
        if (!selfCancelled) {
            showError(errString)
            icon.postDelayed({ callback.onError() },
                ERROR_TIMEOUT_MILLIS
            )
        }
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) =
            showError(helpString)

    override fun onAuthenticationFailed() =
            showError("Try again!")

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
        errorTextView.run {
            removeCallbacks(resetErrorTextRunnable)
            setTextColor(errorTextView.resources.getColor(R.color.success_color, null))
            text = "Success"
        }
        icon.run {
            setImageResource(R.drawable.ic_fingerprint_success)
            postDelayed({ callback.onAuthenticated() },
                SUCCESS_DELAY_MILLIS
            )
        }
    }

    private fun showError(error: CharSequence) {
        icon.setImageResource(R.drawable.ic_fingerprint_error)
        errorTextView.run {
            text = error
            setTextColor(errorTextView.resources.getColor(R.color.warning_color, null))
            removeCallbacks(resetErrorTextRunnable)
            postDelayed(resetErrorTextRunnable,
                ERROR_TIMEOUT_MILLIS
            )
        }
    }

    interface Callback {
        fun onAuthenticated()
        fun onError()
    }

    companion object {
        val ERROR_TIMEOUT_MILLIS: Long = 1600
        val SUCCESS_DELAY_MILLIS: Long = 1300
    }
}

fun canUseFingerprint(): Boolean {
  val activity = ActivityUtils.topActivity
  if (VERSION.SDK_INT >= VERSION_CODES.M && activity != null) {
    // FP.5.1 屏幕锁相关类，用来判断是否用户设置了屏幕锁，如果没有屏幕锁，就不能设置指纹
    val keyguardManager = activity.getSystemService(KeyguardManager::class.java)
    if (!keyguardManager.isKeyguardSecure) return false
    // FP.5.2 指纹认证相关类，用来判断用户是否录入了指纹
    val fingerprintManager = activity.getSystemService(FingerprintManager::class.java)
    if (!fingerprintManager.hasEnrolledFingerprints()) return false
    return true
  } else {
    return false
  }
}
