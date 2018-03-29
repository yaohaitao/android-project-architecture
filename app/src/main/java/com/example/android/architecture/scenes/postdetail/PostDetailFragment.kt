package com.example.android.architecture.scenes.postdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.android.architecture.R
import com.example.android.architecture.models.Post

class PostDetailFragment : Fragment(), PostDetailContract.View {

  override lateinit var presenter: PostDetailContract.Presenter

  private lateinit var titleEditText: EditText
  private lateinit var contentEditText: EditText

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root: View = inflater.inflate(R.layout.post_detail_fragment, container, false)

    titleEditText = root.findViewById(R.id.et_post_detail_title)

    contentEditText = root.findViewById(R.id.et_post_detail_content)

    return root
  }

  override fun onResume() {
    super.onResume()

    presenter.start()
  }

  override fun showPost(post: Post) {
    titleEditText.setText(post.title)
    contentEditText.setText(post.content)
  }

  override fun showNoData() {
    titleEditText.setText("No Title")
    contentEditText.setText("No Content")
  }
}