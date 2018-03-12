package com.example.android.architecture.services;

import com.example.android.architecture.data.remote.apis.PostApi;
import com.example.android.architecture.models.Post;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;

/**
 * Created by YaoHaitao on 2018/3/8.
 */

public class PostService {

    private final PostApi mPostApi;

    public PostService(PostApi postApi) {
        this.mPostApi = postApi;
    }
    // private List<Post> mPosts = new ArrayList<>();
    //
    // public List<Post> fetchPosts() {
    //     // List<Post> posts = new ArrayList<>();
    //     for (int i = 1; i <= 5; i++) {
    //         Post post = new Post(i, "Post title No."+i, "Post content :" + i);
    //         mPosts.add(post);
    //     }
    //     return mPosts;
    // }

    public Call<List<Post>> fetchPosts() {
        return mPostApi.fetchPosts();
    }

    public Call<Post> fetchPostById(int postId) {
        return mPostApi.fetchPostByPostId(postId);
    }

    public Call<Post> insertPost(Post post) {
        Gson gson = new Gson();
        Map<String, Object> postMap = gson.fromJson(gson.toJson(post), Map.class);
        return mPostApi.insertPost(postMap);
    }

    public Call<Post> updatePost(Post post) {
        Gson gson = new Gson();
        Map<String, Object> postMap = gson.fromJson(gson.toJson(post), Map.class );
        return mPostApi.updatePost(postMap);
    }

    public Call<Post> deletePostByPostId(int postId) {
        return mPostApi.deletePostByPostId(postId);
    }

}
