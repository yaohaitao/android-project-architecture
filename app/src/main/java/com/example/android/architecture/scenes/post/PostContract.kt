package com.example.android.architecture.scenes.post

import android.support.v4.app.DialogFragment
import com.example.android.architecture.models.Post
import com.example.android.architecture.scenes.BasePresenter
import com.example.android.architecture.scenes.BaseView

/**
 * Created by YaoHaitao on 2018/3/23.
 */
interface PostContract {

  interface Presenter : BasePresenter {
    /**
     * 加载帖子数据
     */
    fun loadPosts()

    /**
     * 跳转至帖子详情
     */
    //    fun toPostDetailView(withPost: Post)

  }

  interface View : BaseView<Presenter> {
    /**
     * 显示相应的帖子数据
     *
     * @param posts 被展示的帖子数据
     */
    fun showPosts(posts: List<Post>)

    /**
     * 数据为空时设置需要被显示的内容
     */
    fun showNoData()

    /**
     * 页面跳转至帖子详情
     *
     * @param post 被展示的帖子数据
     */
    fun toPostDetailView(postId: Int)

    /**
     * TODO("Add Comment")
     */
    fun setFingerprintSwitchStatus(isOn: Boolean, isEnabled: Boolean)
    fun showDialogFragment(dialogFragment: DialogFragment)
  }
}