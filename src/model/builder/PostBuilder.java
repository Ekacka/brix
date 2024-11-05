package model.builder;

import model.Post;

import java.util.Date;

public class PostBuilder {
    Post post;

    public PostBuilder() {
        post = new Post();
    }

    public PostBuilder setId(Long id) {
        post.setId(id);
        return this;
    }

    public PostBuilder setTitle(String title) {
        post.setTitle(title);
        return this;
    }

    public PostBuilder setContent(String content) {
        post.setContent(content);
        return this;
    }

    public PostBuilder setCreatedAt(Date createdAt) {
        post.setCreatedAt(createdAt);
        return this;
    }

    public PostBuilder setPostedBy(String postedBy) {
        post.setPostedBy(postedBy);
        return this;
    }
}
