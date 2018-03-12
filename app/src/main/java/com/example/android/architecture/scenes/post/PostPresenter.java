package com.example.android.architecture.scenes.post;

import android.support.annotation.NonNull;

import com.example.android.architecture.models.Post;
import com.example.android.architecture.services.PostService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YaoHaitao on 2018/3/9.
 */

public class PostPresenter implements PostContract.Presenter {

    private final PostService mService;
    private final PostContract.View mView;

    public PostPresenter(@NonNull PostService service, @NonNull PostContract.View view) {
        mService = service;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadPosts();
    }

    @Override
    public void loadPosts() {

        Call<List<Post>> call = mService.fetchPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                final List<Post> posts = response.body();
                if (posts.isEmpty()) {
                    mView.showNoData();
                } else {
                    mView.showPosts(posts);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println("Internet Error");
            }
        });


    }

    @Override
    public void toPostDetail(Post targetPost) {
        mView.toPostDetail(targetPost);
    }
}
