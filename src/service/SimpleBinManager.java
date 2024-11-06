package service;

import model.Post;
import java.util.ArrayList;
import java.util.List;

public class SimpleBinManager implements BinManager {

    private final List<Post> binPosts = new ArrayList<>();

    @Override
    public void binPost(Post post) {
        binPosts.add(post);
    }

    @Override
    public Post restorePostById(Long id) {
        for (Post post : binPosts) {
            if (post.getId().equals(id)) {
                binPosts.remove(post);
                return post;
            }
        }
        System.out.println("No bin post found with ID: " + id);
        return null;
    }

    @Override
    public void clearAllBinPosts() {
        binPosts.clear();
        System.out.println("All posts in the bin have been permanently deleted.");
    }

    @Override
    public List<Post> getAllBinPosts() {
        return new ArrayList<>(binPosts);
    }
}
