package com.example.android.architecture.models

data class Result<out T>(
  val status: Int,
  val message: String,
  val data: T? = null
)