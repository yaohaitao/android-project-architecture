package com.example.android.architecture.data.remote.apis;

import com.example.android.architecture.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YaoHaitao on 2018/3/12.
 */

public class ApiProvider {

    private static ApiProvider sApiProvider;
    private final Retrofit retrofit;

    private ApiProvider() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(customInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static ApiProvider getInstance() {
        if (sApiProvider==null){
            synchronized (ApiProvider.class) {
                if (sApiProvider == null)
                    sApiProvider = new ApiProvider();
            }

        }
        return sApiProvider;
    }

    public PostApi makePostApi() {
        return retrofit.create(PostApi.class);
    }

    // TODO
    private Interceptor customInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        };
    }
}
