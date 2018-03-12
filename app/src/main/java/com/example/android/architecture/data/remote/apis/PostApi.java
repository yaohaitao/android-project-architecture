package com.example.android.architecture.data.remote.apis;

import com.example.android.architecture.BuildConfig;
import com.example.android.architecture.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by YaoHaitao on 2018/3/12.
 */

public interface PostApi {

    @GET(BuildConfig.POST_URL)
    Call<List<Post>> fetchPosts();

}
