package com.example.android.architecture.models;

import java.util.Objects;

/**
 * Created by YaoHaitao on 2018/3/8.
 */

public class Post {

    private final int postId;
    private final String title;
    private final String content;

    public Post(int postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

    public int getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return Objects.equals(postId, post.postId) &&
               Objects.equals(title, post.title) &&
               Objects.equals(content, post.content);
    }
}
