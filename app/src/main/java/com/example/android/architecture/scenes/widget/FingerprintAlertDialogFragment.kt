package com.example.android.architecture.scenes.widget

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.example.android.architecture.common.Constants

class FingerprintAlertDialogFragment: DialogFragment() {
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val builder = AlertDialog.Builder(context!!)
    builder.setMessage("Would you like to login with the fingerprint?")
        .setPositiveButton("YES", {
          _, _ ->
          Constants.enableFingerprintLogin = true
        })
        .setNegativeButton("NO", null)

    return builder.create()
  }
}