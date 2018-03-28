package com.example.android.architecture.data.remote

import com.example.android.architecture.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Retrofitのインスタンスを作成する。
 * APIインターフェースをインスタンスできる。
 */
object Network {

  private val retrofit: Retrofit

  init {
    val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(BuildConfig.CONNECT_TIMEOUT, SECONDS)
        .addInterceptor(customInterceptor())
        .build()

    retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
  }

  fun <T> makeApi(type: Class<T>): T = retrofit.create(type)

  private fun customInterceptor(): Interceptor {
    return Interceptor { chain ->
      chain.proceed(chain.request())
    }
  }
}