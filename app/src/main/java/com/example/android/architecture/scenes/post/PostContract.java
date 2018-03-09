package com.example.android.architecture.scenes.post;

import com.example.android.architecture.models.Post;
import com.example.android.architecture.scenes.BasePresenter;
import com.example.android.architecture.scenes.BaseView;

import java.util.List;

/**
 * Created by YaoHaitao on 2018/3/9.
 */

public interface PostContract {

    interface View extends BaseView<Presenter> {

        void showPosts(List<Post> posts);

        void showNoData();

        void toPostDetail(Post post);
    }

    interface Presenter extends BasePresenter {

        void loadPosts();

        void toPostDetail(Post targetPost);
    }
}
