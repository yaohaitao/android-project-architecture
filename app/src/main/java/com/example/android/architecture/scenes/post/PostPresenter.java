package com.example.android.architecture.scenes.post;

import android.support.annotation.NonNull;

import com.example.android.architecture.models.Post;
import com.example.android.architecture.services.PostService;

import java.util.List;

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
        List<Post> posts = mService.fetchPosts();

        if (posts.isEmpty()) {
            mView.showNoData();
        } else {
            mView.showPosts(posts);
        }
    }

    @Override
    public void toPostDetail(Post targetPost) {
        mView.toPostDetail(targetPost);
    }
}
