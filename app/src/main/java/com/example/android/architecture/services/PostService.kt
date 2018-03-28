package com.example.android.architecture.services

import com.example.android.architecture.data.remote.PostApi
import com.example.android.architecture.models.Post
import com.google.gson.Gson

/**
 * ポストサービス
 * ポストに関する操作はここで実装される。
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