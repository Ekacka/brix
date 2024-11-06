package service;

import model.Post;
import java.util.List;

public abstract class PostServiceDecorator implements PostService {
    protected final PostService wrappedService;

    public PostServiceDecorator(PostService wrappedService) {
        this.wrappedService = wrappedService;
    }

    @Override
    public void createPost(Post post) {
        wrappedService.createPost(post);
    }

    @Override
    public void updatePost(Long id, Post newPost) {
        wrappedService.updatePost(id, newPost);
    }

    @Override
    public void deletePost(Long id) {
        wrappedService.deletePost(id);
    }

    @Override
    public Post getPostById(Long id) {
        return wrappedService.getPostById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return wrappedService.getAllPosts();
    }

    @Override
    public void archivePost(Long id) {
        wrappedService.archivePost(id);
    }

    @Override
    public List<Post> getArchivedPosts() {
        return wrappedService.getArchivedPosts();
    }

    @Override
    public Class<?> getClassToBind() {
        return wrappedService.getClassToBind();
    }
}
