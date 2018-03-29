package com.example.android.architecture.scenes.post

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.architecture.R
import com.example.android.architecture.utils.addFragment
import com.example.android.architecture.utils.makePostService

/**
 * ポストのアクティビティ
 */
class PostActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.post_activity)

    var postFragment =
      super.getSupportFragmentManager().findFragmentById(R.id.fl_post_container) as PostFragment?

    if (postFragment == null) {
      postFragment = PostFragment()
      addFragment(
          super.getSupportFragmentManager(), postFragment, R.id.fl_post_container
      )
    }

    PostPresenter(makePostService(), postFragment)
  }
}