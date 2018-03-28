package com.example.android.architecture.utils

import com.example.android.architecture.services.PostService

/**
 * サービスのインスタンスを提供する。
 */
object ServiceProvider {

  fun makePostService(): PostService =
    PostService(
        ApiProvider.makePostApi()
    )
}