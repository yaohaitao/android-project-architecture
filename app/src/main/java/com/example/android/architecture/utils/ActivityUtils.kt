package com.example.android.architecture.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import org.jetbrains.annotations.NotNull

/**
 * アクティビティーに関する操作を提供する。
 */
object ActivityUtils {

  fun addFragmentToActivity(
    @NotNull fragmentManager: FragmentManager,
    @NotNull fragment: Fragment,
    frameId: Int
  ) {
    checkNotNull(fragmentManager)
    checkNotNull(fragment)
    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.add(frameId, fragment)
    fragmentTransaction.commit()
  }
}