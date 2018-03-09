package com.example.android.architecture.models;

import java.util.Objects;

/**
 * Created by YaoHaitao on 2018/3/8.
 */

public class Post {

    private final int mPostId;
    private final String mTitle;
    private final String mContent;

    public Post(int postId, String title, String content) {
        mPostId = postId;
        mTitle = title;
        mContent = content;
    }

    public int getPostId() {
        return mPostId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return Objects.equals(mPostId, post.mPostId) &&
               Objects.equals(mTitle, post.mTitle) &&
               Objects.equals(mContent, post.mContent);
    }
}
