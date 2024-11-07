package service;

import database.PostRepository;
import exception.PostNotFoundException;
import model.Post;

import java.util.ArrayList;
import java.util.List;

public class RuntimePostService implements PostService {
    private final PostRepository postRepo;
    private final List<Post> archivedPosts = new ArrayList<>();  // New archive list

    public RuntimePostService(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public void createPost(Post post) {
        postRepo.save(post);
    }

    @Override
    public void updatePost(Long id, Post newPost) {
        postRepo.update(id, newPost);
    }

    @Override
    public void deletePost(Long id) {
        postRepo.delete(id);
    }

    @Override
    public Post getPostById(Long id) {
        try {
            return postRepo.findById(id);
        } catch (PostNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Class<?> getClassToBind() {
        return PostService.class;
    }
}
