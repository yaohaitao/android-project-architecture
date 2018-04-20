package com.example.android.architecture.services

import com.example.android.architecture.data.remote.apis.UserApi
import com.example.android.architecture.models.Result
import com.example.android.architecture.models.User
import com.google.gson.Gson
import io.reactivex.BackpressureStrategy.MISSING
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserService constructor(private val userApi: UserApi) {

  fun userLogin(user: User) = {
    //    val gson = Gson()
//    val userMap = gson.fromJson<Map<String, Any>>(gson.toJson(user))
//    userApi.authenticate(userMap = userMap)

    Flowable.create({ emitter: FlowableEmitter<Result<User>> ->
      if (user.username == "admin" && user.password == "admin") {
        val result = Result(status = 1, message = "Login Successfully!", data = user)
        emitter.onNext(result)
      } else {
        val result = Result<User>(status = 0, message = "Error username or password!")
        emitter.onNext(result)
      }
    }, MISSING)
        .observeOn(Schedulers.computation())
        .subscribeOn(AndroidSchedulers.mainThread())
  }()

  private inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, T::class.java)
  }
}