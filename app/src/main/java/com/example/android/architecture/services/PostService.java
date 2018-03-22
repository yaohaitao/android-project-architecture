package com.example.android.architecture.services;

import com.example.android.architecture.data.remote.apis.PostApi;
import com.example.android.architecture.models.Post;
import com.google.gson.Gson;
import io.reactivex.Flowable;
import java.util.List;
import java.util.Map;

/**
 * Created by YaoHaitao on 2018/3/8.
 */

public class PostService {

  private final PostApi mPostApi;

  public PostService(PostApi postApi) {
    this.mPostApi = postApi;
  }

  public Flowable<List<Post>> fetchPosts() {
    return mPostApi.fetchPosts();
  }

  public Flowable<Post> fetchPostById(int postId) {
    return mPostApi.fetchPostByPostId(postId);
  }

  public Flowable<Post> insertPost(Post post) {
    Gson gson = new Gson();
    Map<String, Object> postMap = gson.fromJson(gson.toJson(post), Map.class);
    return mPostApi.insertPost(postMap);
  }

  public Flowable<Post> updatePost(Post post) {
    Gson gson = new Gson();
    Map<String, Object> postMap = gson.fromJson(gson.toJson(post), Map.class);
    return mPostApi.updatePost(postMap);
  }

  public Flowable<Post> deletePostByPostId(int postId) {
    return mPostApi.deletePostByPostId(postId);
  }
}
