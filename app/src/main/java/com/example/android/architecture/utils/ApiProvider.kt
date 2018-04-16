package com.example.android.architecture.utils

import com.example.android.architecture.data.remote.Network
import com.example.android.architecture.data.remote.apis.PostApi

/**
 * Created by YaoHaitao on 2018/3/23.
 */
fun makePostApi(): PostApi = Network.makeApi(
    PostApi::class.java)
