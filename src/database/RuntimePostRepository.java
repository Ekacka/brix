package database;

import exception.PostNotFoundException;
import model.Post;

import java.util.ArrayList;
import java.util.List;

public class RuntimePostRepository implements PostRepository {

    List<Post> posts;

    public RuntimePostRepository() {
        posts = new ArrayList<>();
    }

    @Override
    public void save(Post post) {
        if(!posts.contains(post)) {
            posts.add(post);
        }
    }

    @Override
    public Post findById(Long id) throws PostNotFoundException {
        Post post = posts.stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
        if(post == null) {
            throw new PostNotFoundException("Post with id %d was not found".formatted(id));
        }
        return post;
    }

    @Override
    public List<Post> findAll() {
        return posts;
    }

    @Override
    public void update(Long id, Post newPost) {
        Post post;
        try {
            post = findById(id);
        } catch (PostNotFoundException e) {
            e.printStackTrace();
            return;
        }
        post.setPostedBy(newPost.getPostedBy());
        post.setContent(newPost.getContent());
        post.setCreatedAt(newPost.getCreatedAt());
        post.setTitle(newPost.getTitle());
    }

    @Override
    public void delete(Long id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
