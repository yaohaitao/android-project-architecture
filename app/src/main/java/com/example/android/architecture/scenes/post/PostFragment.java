package com.example.android.architecture.scenes.post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.architecture.R;
import com.example.android.architecture.models.Post;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YaoHaitao on 2018/3/9.
 */

public class PostFragment extends Fragment implements PostContract.View {

  //region 组件声明
  private RecyclerView mRecyclerView;
  private TextView mNoDataTextView;
  //endregion

  //region 私有变量声明
  private PostContract.Presenter mPresenter;
  private PostAdapter mPostAdapter;
  //endregion

  //region 初始化
  public PostFragment() {
    super();
  }

  public static PostFragment newInstance() {
    return new PostFragment();
  }
  //endregion

  //region View 生命周期
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPostAdapter = new PostAdapter(new ArrayList<Post>());
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.post_fragment, container, false);

    mRecyclerView = root.findViewById(R.id.rv_post);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.setAdapter(mPostAdapter);

    mNoDataTextView = root.findViewById(R.id.tv_post_empty);

    return root;
  }

  @Override
  public void onResume() {
    super.onResume();

    mPresenter.start();
  }
  //endregion

  //region View Interface
  @Override
  public void setPresenter(PostContract.Presenter presenter) {
    this.mPresenter = presenter;
  }

  @Override
  public void showPosts(List<Post> posts) {
    mPostAdapter.replaceData(posts);

    mRecyclerView.setVisibility(View.VISIBLE);
    mNoDataTextView.setVisibility(View.GONE);
  }

  @Override
  public void showNoData() {
    mRecyclerView.setVisibility(View.GONE);
    mNoDataTextView.setVisibility(View.VISIBLE);
  }

  @Override
  public void toPostDetail(Post post) {
    // TODO
  }
  //endregion

  //region Recycler View
  private class PostHolder extends RecyclerView.ViewHolder {

    private Post mPost;

    private TextView mTitleTextView;
    private TextView mContentTextVIew;

    PostHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.post_recycler_item_fragment, parent, false));

      mTitleTextView = itemView.findViewById(R.id.tv_post_item_title);
      mContentTextVIew = itemView.findViewById(R.id.tv_post_item_content);

      mTitleTextView.setText("");
      mContentTextVIew.setText("");

      itemView.setOnClickListener(
          view -> mPresenter.toPostDetail(mPost)
      );
    }

    void binding(Post post) {
      mPost = post;

      mTitleTextView.setText(post.getTitle());
      mContentTextVIew.setText(post.getContent());
    }
  }

  private class PostAdapter extends RecyclerView.Adapter<PostHolder> {

    private List<Post> mPosts;

    PostAdapter(List<Post> posts) {
      setPosts(posts);
    }

    void replaceData(List<Post> posts) {
      setPosts(posts);
      notifyDataSetChanged();
    }

    private void setPosts(List<Post> posts) {
      mPosts = posts;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(getActivity());
      return new PostHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
      Post post = mPosts.get(position);
      holder.binding(post);
    }

    @Override
    public int getItemCount() {
      return mPosts.size();
    }
  }
  //endregion
}
