package com.example.android.architecture.scenes.post

import com.example.android.architecture.models.Post
import com.example.android.architecture.scenes.post.PostContract.Presenter
import com.example.android.architecture.scenes.post.PostContract.View
import com.example.android.architecture.services.PostService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * ポストのプレゼンター
 */
class PostPresenter(
  private val service: PostService,
  private val view: View
) : Presenter {

  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  init {
    view.presenter = this
  }

  override fun start() {
    loadPosts()
  }

  override fun loadPosts() {

    val flowable = service.fetchPosts()

    val disposable: Disposable = flowable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ postList: List<Post>? ->
          if (postList == null || postList.isEmpty()) {
            view.showNoData()
          } else {
            view.showPosts(postList)
          }
        }, { error: Throwable? ->
          error!!.printStackTrace()
        })

    compositeDisposable.add(disposable)
  }
}