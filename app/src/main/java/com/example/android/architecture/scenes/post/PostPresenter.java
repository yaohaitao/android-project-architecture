package com.example.android.architecture.scenes.post;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.architecture.models.Post;
import com.example.android.architecture.services.PostService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

        Observable<List<Post>> observablePosts = mService.fetchPosts();

        observablePosts.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("Internet", "On Subscribe");
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        if (posts.isEmpty()) {
                            mView.showNoData();
                        } else {
                            mView.showPosts(posts);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Internet", "On Complete");
                    }
                });
    }

    @Override
    public void toPostDetail(Post targetPost) {
        mView.toPostDetail(targetPost);
    }
}
