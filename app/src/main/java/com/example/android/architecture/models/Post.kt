package com.example.android.architecture.models

/**
 * Created by YaoHaitao on 2018/3/22.
 */

data class Post(
  val postId: Int,
  val title: String = "No title",
  val content: String = "No Content"
)