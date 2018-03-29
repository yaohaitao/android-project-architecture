package com.example.android.architecture.scenes.postdetail

import com.example.android.architecture.models.Post
import com.example.android.architecture.services.PostService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by YaoHaitao on 2018/3/28.
 */
class PostDetailPresenter constructor(
  private val service: PostService,
  private val view: PostDetailContract.View,
  private val postId: Int
) : PostDetailContract.Presenter {

  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  init {
    view.presenter = this
  }

  override fun start() {
    loadPost()
  }

  fun loadPost() {

    val flowable = service.fetchPost(postId)

    val disposable: Disposable = flowable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ post: Post? ->
          if (post == null) {
            view.showNoData()
          } else {
            view.showPost(post)
          }
        }, { error: Throwable? ->
          error!!.printStackTrace()
        })

    compositeDisposable.add(disposable)
  }
}