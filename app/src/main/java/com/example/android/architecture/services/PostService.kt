package com.example.android.architecture.services

import com.example.android.architecture.data.remote.apis.PostApi
import com.example.android.architecture.models.Post
import com.google.gson.Gson

/**
 * Created by YaoHaitao on 2018/3/23.
 */
class PostService constructor(val postApi: PostApi) {

  fun fetchPosts() = postApi.fetchPosts()

  fun fetchPost(id: Int) = postApi.fetchPost(id)

  fun createPost(post: Post) = {
    val gson = Gson()
    val postMap = gson.fromJson<Map<String, Any?>>(gson.toJson(post))
//    val postMap: Map<String, Any?> = gson.fromJson(gson.toJson(post), Map::class.java) as Map<String, Any?>
    postApi.createPost(postMap)
  }()

  fun updatePost(post: Post) = {
    val gson = Gson()
    val postMap = gson.fromJson<Map<String, Any?>>(gson.toJson(post))
//    val postMap: Map<String, Any?> = gson.fromJson(gson.toJson(post), Map::class.java) as Map<String, Any?>
    postApi.updatePost(postMap)
  }()

  fun deletePost(id: Int) = postApi.deletePost(id)

  private inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, T::class.java)
  }
}