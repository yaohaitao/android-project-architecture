package com.example.android.architecture.data.remote.apis;

import com.example.android.architecture.BuildConfig;
import com.example.android.architecture.models.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by YaoHaitao on 2018/3/12.
 */

public interface PostApi {

    @GET(BuildConfig.POST_URL)
    Call<List<Post>> fetchPosts();

    @GET(BuildConfig.POST_URL)
    Call<Post> fetchPostByPostId(@Query("postId") int postId);

    @POST(BuildConfig.POST_URL)
    @FormUrlEncoded
    Call<Post> insertPost(@FieldMap Map<String, Object> postMap);

    @PUT(BuildConfig.POST_URL)
    @FormUrlEncoded
    Call<Post> updatePost(@FieldMap Map<String, Object> postMap);

    @DELETE(BuildConfig.POST_URL)
    Call<Post> deletePostByPostId(@Query("postId") int postId);

}
