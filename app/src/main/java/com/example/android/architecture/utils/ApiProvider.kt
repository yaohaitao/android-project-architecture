package com.example.android.architecture.utils

import com.example.android.architecture.data.remote.Network
import com.example.android.architecture.data.remote.apis.PostApi
import com.example.android.architecture.data.remote.apis.UserApi

object ApiProvider {

  fun makePostApi() = Network.makeApi(PostApi::class.java)

  fun makeUserApi() = Network.makeApi(UserApi::class.java)
}

