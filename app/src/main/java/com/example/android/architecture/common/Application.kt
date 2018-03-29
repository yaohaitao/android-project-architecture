package com.example.android.architecture.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.LinkedList

/**
 * アプリケーションの初期化
 */
class Application : Application() {

  companion object {
    @SuppressLint("StaticFieldLeak")
    lateinit var application: Application
    val activityList: MutableList<Activity> = LinkedList()
  }

  override fun onCreate() {
    super.onCreate()

    application = this
    application.registerActivityLifecycleCallbacks(callbacks)



  }

  private val callbacks = object : ActivityLifecycleCallbacks {
    override fun onActivityCreated(
      activity: Activity?,
      savedInstanceState: Bundle?
    ) {
      setTopActivity(activity!!)
    }

    override fun onActivityStarted(activity: Activity?) {
      setTopActivity(activity!!)
    }

    override fun onActivityResumed(activity: Activity?) {
      setTopActivity(activity!!)
    }

    override fun onActivityDestroyed(activity: Activity?) {
      activityList.remove(activity!!)
    }

    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivitySaveInstanceState(
      activity: Activity?,
      outState: Bundle?
    ) {
    }
  }

  private fun setTopActivity(activity: Activity) {
    if (activityList.contains(activity)) {
      if (activityList.last() != activity) {
        activityList.remove(activity)
        activityList.add(activity)
      }
    } else {
      activityList.add(activity)
    }
  }
}