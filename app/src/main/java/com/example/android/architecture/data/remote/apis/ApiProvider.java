package com.example.android.architecture.data.remote.apis;

import com.example.android.architecture.data.remote.Network;


/**
 * Created by YaoHaitao on 2018/3/12.
 */

public class ApiProvider {

    private ApiProvider() {}

    public static PostApi makePostAPI() {
        return Network.getInstance().makeApi(PostApi.class);
    }

}