package service;

import model.Post;

import java.util.List;

public interface PostService extends Service {
    void createPost(Post post);
    void updatePost(Long id, Post newPost);
    void deletePost(Long id);
    Post getPost(Long id);
    List<Post> getAllPosts();
}
