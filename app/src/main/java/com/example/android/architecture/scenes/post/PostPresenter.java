package com.example.android.architecture.scenes.post;

import android.support.annotation.NonNull;
import com.example.android.architecture.models.Post;
import com.example.android.architecture.services.PostService;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;

/**
 * Created by YaoHaitao on 2018/3/9.
 */

public class PostPresenter implements PostContract.Presenter {

  private final PostService mService;
  private final PostContract.View mView;
  private CompositeDisposable mCompositeDisposable;

  public PostPresenter(@NonNull PostService service, @NonNull PostContract.View view) {
    mService = service;
    mView = view;

    mCompositeDisposable = new CompositeDisposable();
    mView.setPresenter(this);
  }

  @Override
  public void start() {
    loadPosts();
  }

  @Override
  public void loadPosts() {

    Flowable<List<Post>> flowable = mService.fetchPosts();

    // Error
    Disposable disposable = flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(
        posts -> {
          if (posts.isEmpty()) {
            mView.showNoData();
          } else {
            mView.showPosts(posts);
          }
        },
        Throwable::printStackTrace
    );

    mCompositeDisposable.add(disposable);
  }

  @Override
  public void toPostDetail(Post targetPost) {
    mView.toPostDetail(targetPost);
  }
}
