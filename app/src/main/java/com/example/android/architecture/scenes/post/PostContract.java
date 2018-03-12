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

        /**
         * 显示相应的帖子数据
         * @param posts 被展示的帖子数据
         */
        void showPosts(List<Post> posts);

        /**
         * 数据为空时设置需要被显示的内容
         */
        void showNoData();

        /**
         * 页面跳转至帖子详情
         * @param post 被展示的帖子数据
         */
        void toPostDetail(Post post);
    }

    interface Presenter extends BasePresenter {

        /**
         * 加在帖子数据
         */
        void loadPosts();

        /**
         * 跳转至帖子详情
         * @param targetPost 被展示的帖子数据
         */
        void toPostDetail(Post targetPost);
    }
}
