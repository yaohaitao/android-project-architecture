@file:JvmName("ActivityUtils")
package com.example.android.architecture.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.example.android.architecture.common.Application
import org.jetbrains.annotations.NotNull

val topActivity: Activity?
  get() {
    return Application.activityList.lastOrNull()
  }

fun finishActivity(
  activity: Activity,
  animate: Boolean = true
) {
  activity.finish()
  if (!animate) {
    activity.overridePendingTransition(0, 0)
  }
}

fun addFragment(
  @NotNull fragmentManager: FragmentManager,
  @NotNull fragment: Fragment,
  containerId: Int
) {
  checkNotNull(fragmentManager)
  checkNotNull(fragment)
  val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
  fragmentTransaction.add(containerId, fragment)
  fragmentTransaction.commit()
}

@SuppressLint("ObsoleteSdkInt")
fun <T> startActivity(
  context: Context = getActivityOrApp(),
  pkg: String = context.packageName,
  cls: Class<T>,
  extras: Bundle? = null,
  options: Bundle? = null
) {
  val intent = Intent(Intent.ACTION_VIEW)

  context.let {
    if (it !is Activity) {
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
  }

  extras?.let {
    intent.putExtras(it)
  }

  intent.component = ComponentName(pkg, cls.name)

  if (options != null && SDK_INT >= JELLY_BEAN) {
    context.startActivity(intent, options)
  } else {
    context.startActivity(intent)
  }
}

fun finishToActivity(
  activity: Activity,
  includeSelf: Boolean = false,
  animate: Boolean = true
) {
  val activityList = Application.activityList.toList()
  for (existingActivity in activityList.reversed()) {
    if (existingActivity == activity) {
      if (includeSelf) {
        finishActivity(activity, animate)
      }
      return
    }
    finishActivity(existingActivity, animate)
  }
}

private fun getActivityOrApp(): Context {
  return topActivity ?: return Application.application
}