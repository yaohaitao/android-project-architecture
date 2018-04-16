package com.example.android.architecture.utils

import com.example.android.architecture.services.PostService

/**
 * Created by YaoHaitao on 2018/3/23.
 */

fun makePostService(): PostService =
  PostService(
      makePostApi()
  )


