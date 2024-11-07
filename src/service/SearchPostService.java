package service;

import database.PostRepository;
import model.Post;

import java.util.List;

public class SearchPostService extends PostServiceDecorator {

    PostRepository postRepo;
    public SearchPostService(PostService wrappedService, PostRepository postRepo) {
        super(wrappedService);
        this.postRepo = postRepo;
    }

    @Override
    public Post searchPostByTitle(String title) {
        List<Post> posts = postRepo.findAll();
        return posts.stream().filter(post -> post.getTitle().equals(title)).findFirst().orElse(null);
    }

    @Override
    public Class<?> getClassToBind() {
        return PostServiceDecorator.class;
    }
}
