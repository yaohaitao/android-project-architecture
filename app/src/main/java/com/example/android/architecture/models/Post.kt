package com.example.android.architecture.models

data class Post(
  val postId: Int,
  val title: String = "No title",
  val content: String = "No Content"
)