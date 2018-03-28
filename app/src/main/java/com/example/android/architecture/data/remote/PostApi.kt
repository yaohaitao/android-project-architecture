package com.example.android.architecture.data.remote

import com.example.android.architecture.BuildConfig
import com.example.android.architecture.models.Post
import io.reactivex.Flowable
import retrofit2.http.DELETE
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 *　サーバーと通信する時、詳しく定義する
 */
interface PostApi {

  @GET(BuildConfig.POST_URL)
  fun fetchPosts(): Flowable<List<Post>?>

  @GET(BuildConfig.POST_URL)
  fun fetchPost(@Query("postId") id: Int): Flowable<Post?>

  @POST(BuildConfig.POST_URL)
  @FormUrlEncoded
  fun createPost(@FieldMap postMap: Map<String, Any?>): Flowable<Post?>

  @PUT(BuildConfig.POST_URL)
  @FormUrlEncoded
  fun updatePost(@FieldMap postMap: Map<String, Any?>): Flowable<Post?>

  @DELETE(BuildConfig.POST_URL)
  fun deletePost(@Query("postId") id: Int): Flowable<Post?>
}