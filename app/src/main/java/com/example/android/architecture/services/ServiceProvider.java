package com.example.android.architecture.services;

import com.example.android.architecture.data.remote.apis.ApiProvider;
import com.example.android.architecture.data.remote.apis.PostApi;

/**
 * Created by YaoHaitao on 2018/3/12.
 */

public class ServiceProvider {

    public static PostService makePostService() {
        return new PostService(ApiProvider.getInstance().makeApi(PostApi.class));
    }

}
