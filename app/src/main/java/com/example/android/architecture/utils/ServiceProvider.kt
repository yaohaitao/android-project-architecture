@file:JvmName("ServiceProvider")
package com.example.android.architecture.utils

import com.example.android.architecture.services.PostService

fun makePostService(): PostService =
  PostService(
      makePostApi()
  )