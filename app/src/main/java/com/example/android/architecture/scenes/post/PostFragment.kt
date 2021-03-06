package com.example.android.architecture.scenes.post

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.example.android.architecture.R
import com.example.android.architecture.common.Constants
import com.example.android.architecture.models.Post
import com.example.android.architecture.scenes.post.PostContract.Presenter
import com.example.android.architecture.scenes.postdetail.EXTRA_POST_ID
import com.example.android.architecture.scenes.postdetail.PostDetailActivity
import com.example.android.architecture.utils.ActivityUtils

/**
 * ポストのフラグメント
 */
class PostFragment : Fragment(), PostContract.View {

  override lateinit var presenter: Presenter

  private lateinit var recyclerView: RecyclerView
  private lateinit var noDataTextView: TextView
  private lateinit var fingerprintSwitch: Switch

  private lateinit var postAdapter: PostAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    postAdapter = PostAdapter(ArrayList())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root: View = inflater.inflate(R.layout.post_fragment, container, false)

    recyclerView = root.findViewById<RecyclerView>(R.id.rv_post).apply {
      layoutManager = LinearLayoutManager(activity)
      itemAnimator = DefaultItemAnimator()
      adapter = postAdapter
    }

    noDataTextView = root.findViewById(R.id.tv_post_empty)

    fingerprintSwitch = root.findViewById<Switch>(R.id.sw_post_fingerprint).apply {
      setOnCheckedChangeListener { _, isChecked ->
        Constants.enableFingerprintLogin = isChecked
      }
    }

    return root
  }

  override fun onResume() {
    super.onResume()

    presenter.start()
  }

  override fun showPosts(posts: List<Post>) {
    postAdapter.replacePostList(posts)

    recyclerView.visibility = View.VISIBLE
    noDataTextView.visibility = View.GONE
  }

  override fun showNoData() {
    recyclerView.visibility = View.GONE
    noDataTextView.visibility = View.VISIBLE
  }

  override fun toPostDetailView(postId: Int) {
    val bundle = Bundle()
    bundle.putInt(EXTRA_POST_ID, postId)
    ActivityUtils.startActivity(cls = PostDetailActivity::class.java, extras = bundle)
  }

  override fun setFingerprintSwitchStatus(
    isOn: Boolean,
    isEnabled: Boolean
  ) {
    fingerprintSwitch.isChecked = isOn
    fingerprintSwitch.isEnabled = isEnabled
    if (!isEnabled) fingerprintSwitch.text = getString(R.string.fingerprint_switch_not_supported)
  }

  override fun showDialogFragment(dialogFragment: DialogFragment) {
    dialogFragment.show(fragmentManager, "TestTest")
  }

  inner class PostHolder internal constructor(
    inflater: LayoutInflater,
    val parent: ViewGroup
  ) : ViewHolder(inflater.inflate(R.layout.post_recycler_item_fragment, parent, false)) {

    private lateinit var post: Post

    private val titleTextView: TextView = itemView.findViewById(R.id.tv_post_item_title)
    private val contentTextView: TextView = itemView.findViewById(R.id.tv_post_item_content)

    init {
      itemView.setOnClickListener({
        toPostDetailView(post.postId)
      })
    }

    fun binding(post: Post) {
      this.post = post

      titleTextView.text = this.post.title
      contentTextView.text = this.post.content
    }
  }

  inner class PostAdapter constructor(private var postList: List<Post>) : Adapter<PostHolder>() {

    fun replacePostList(postList: List<Post>) {
      this.postList = postList
      notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): PostHolder {
      val inflater: LayoutInflater = LayoutInflater.from(activity)
      return PostHolder(inflater, parent)
    }

    override fun onBindViewHolder(
      holder: PostHolder,
      position: Int
    ) {
      val post = postList[position]
      holder.binding(post)
    }

    override fun getItemCount(): Int = postList.size
  }
}
