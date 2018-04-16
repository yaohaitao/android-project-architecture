package com.example.android.architecture.scenes.postdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.architecture.R
import com.example.android.architecture.utils.addFragment
import com.example.android.architecture.utils.makePostService

const val EXTRA_POST_ID = "POST_ID"
/**
 * Created by YaoHaitao on 2018/3/28.
 */
class PostDetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.post_detail_activity)

    val postId: Int = intent.getIntExtra(EXTRA_POST_ID, -1)

    var postDetailFragment = super.getSupportFragmentManager().findFragmentById(R.id.fl_post_detail_container) as PostDetailFragment?

    if (postDetailFragment == null) {
      postDetailFragment = PostDetailFragment()
      addFragment(super.getSupportFragmentManager(), postDetailFragment, R.id.fl_post_detail_container)
    }

    PostDetailPresenter(makePostService(), postDetailFragment, postId)
  }

}