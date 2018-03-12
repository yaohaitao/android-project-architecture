package com.example.android.architecture.services;

import com.example.android.architecture.data.remote.apis.PostApi;
import com.example.android.architecture.models.Post;

import java.util.ArrayList;
import java.util.List;

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


}
