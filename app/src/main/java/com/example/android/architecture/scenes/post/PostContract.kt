package com.example.android.architecture.scenes.post

import android.support.v4.app.DialogFragment
import com.example.android.architecture.models.Post
import com.example.android.architecture.scenes.BasePresenter
import com.example.android.architecture.scenes.BaseView

interface PostContract {
  /**
   * ポストプレゼンターのインターフェース。
   * ロジック処理に関する方法はここで定義する。
    */
  interface Presenter : BasePresenter {
    /**
     * ポストデーターの読込。
     */
    fun loadPosts()

    /**
     * 一つポストアイテムを押したら実行される。
     */
    //    fun toPostDetailView(withPost: Post)

  }

  /**
   * ポストビューのインターフェース。
   * 画面に関する方法はここで定義する。
   */
  interface View : BaseView<Presenter> {
    /**
     * ポストデーターを表示させる。
     *
     * @param posts 表示されるべきポストデーター。
     */
    fun showPosts(posts: List<Post>)

    /**
     * データーは空だったら表示させるべきビュー。
     */
    fun showNoData()

    /**
     * ポストディティール画面へ遷移する。
     *
     * @param postId ディティール画面で、表示されるべきポスト。
     */
    fun toPostDetailView(postId: Int)

    /**
     * TODO("Add Comment")
     */
    fun setFingerprintSwitchStatus(isOn: Boolean, isEnabled: Boolean)
    fun showDialogFragment(dialogFragment: DialogFragment)
  }
}
