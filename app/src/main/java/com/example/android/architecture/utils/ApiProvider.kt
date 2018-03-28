package com.example.android.architecture.utils

import com.example.android.architecture.data.remote.Network
import com.example.android.architecture.data.remote.PostApi

/**
 * サーバーと接続できるAPIインスタンスを提供。
 */
object ApiProvider {

  fun makePostApi(): PostApi = Network.makeApi(
      PostApi::class.java)
}