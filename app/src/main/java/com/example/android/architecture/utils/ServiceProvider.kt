package com.example.android.architecture.utils

import com.example.android.architecture.services.PostService
import com.example.android.architecture.services.UserService

object ServiceProvider {

  fun makePostService() = PostService(ApiProvider.makePostApi())

  fun makeUserService() = UserService(ApiProvider.makeUserApi())
}




