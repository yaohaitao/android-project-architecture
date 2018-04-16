package com.example.android.architecture.scenes.postdetail

import com.example.android.architecture.models.Post
import com.example.android.architecture.scenes.BasePresenter
import com.example.android.architecture.scenes.BaseView

/**
 * Created by YaoHaitao on 2018/3/28.
 */

interface PostDetailContract {

  interface Presenter : BasePresenter

  interface View : BaseView<PostDetailContract.Presenter> {
    fun showPost(post: Post)

    fun showNoData()
  }
}
