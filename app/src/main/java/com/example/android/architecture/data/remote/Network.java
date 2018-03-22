package com.example.android.architecture.data.remote;

import com.example.android.architecture.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YaoHaitao on 2018/3/9.
 */

public class Network {

  private static Network sNetwork;
  private final Retrofit retrofit;

  private Network() {
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        .connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(customInterceptor())
        .build();

    retrofit = new Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build();
  }

  public static synchronized Network getInstance() {
    if (sNetwork == null) {
      sNetwork = new Network();
    }
    return sNetwork;
  }

  public <T> T makeApi(Class<T> apiType) {
    return retrofit.create(apiType);
  }

  // TODO
  private Interceptor customInterceptor() {
    return chain -> {
      return chain.proceed(chain.request());
    };
  }
}
