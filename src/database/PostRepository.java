package database;

import exception.PostNotFoundException;
import model.Post;

import java.util.List;

public interface PostRepository {
    void save(Post post);
    Post findById(Long id) throws PostNotFoundException;
    List<Post> findAll();
    void update(Long id, Post newPost);
    void delete(Long id);
}
