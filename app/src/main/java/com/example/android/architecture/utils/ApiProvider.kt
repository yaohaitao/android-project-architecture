@file:JvmName("ApiProvider")
package com.example.android.architecture.utils

import com.example.android.architecture.data.remote.Network
import com.example.android.architecture.data.remote.PostApi

fun makePostApi(): PostApi = Network.makeApi(
    PostApi::class.java)