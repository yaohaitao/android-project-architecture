package com.example.android.architecture.data.remote.apis

import com.example.android.architecture.BuildConfig
import com.example.android.architecture.models.Result
import com.example.android.architecture.models.User
import io.reactivex.Flowable
import retrofit2.http.FieldMap
import retrofit2.http.POST

interface UserApi {

  @POST(BuildConfig.USER_URL)
  fun authenticate(@FieldMap userMap: Map<String, Any>) : Flowable<Result<User>?>
}