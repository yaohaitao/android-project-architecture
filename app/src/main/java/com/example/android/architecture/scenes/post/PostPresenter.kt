package com.example.android.architecture.scenes.post

import com.example.android.architecture.common.Constants
import com.example.android.architecture.models.Post
import com.example.android.architecture.scenes.post.PostContract.Presenter
import com.example.android.architecture.scenes.post.PostContract.View
import com.example.android.architecture.scenes.widget.FingerprintAlertDialogFragment
import com.example.android.architecture.scenes.widget.canUseFingerprint
import com.example.android.architecture.services.PostService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by YaoHaitao on 2018/3/23.
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
    if (canUseFingerprint()) {
      compositeDisposable.add(
          Constants.enableFingerprintLoginObservable.subscribeOn(AndroidSchedulers.mainThread())
              .subscribe {
                view.setFingerprintSwitchStatus(it, true)
              })

      if (!Constants.enableFingerprintLogin) {
        view.showDialogFragment(FingerprintAlertDialogFragment())
      }
    } else {
      view.setFingerprintSwitchStatus(false, false)
    }
    loadPosts()
  }

  override fun loadPosts() {

    val disposable: Disposable = service.fetchPosts()
        .subscribeOn(Schedulers.io())
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