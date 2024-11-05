package service;

import database.PostRepository;
import exception.PostNotFoundException;
import model.Post;

import java.util.List;

public class RuntimePostService implements PostService {
    private PostRepository postRepo;

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
    public Post getPost(Long id) {
        Post post;
        try {
            post = postRepo.findById(id);
        } catch (PostNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }
}
