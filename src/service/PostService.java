package service;

import model.Post;

import java.util.List;

public interface PostService extends Service {
    void createPost(Post post);
    void updatePost(Long id, Post newPost);
    void deletePost(Long id);
    Post getPostById(Long id);
    List<Post> getAllPosts();
}
