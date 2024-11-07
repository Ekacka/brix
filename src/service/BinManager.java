package service;

import model.Post;
import java.util.List;

public interface BinManager extends Service {
    void binPost(Post post);  // Move post to bin
    Post restorePostById(Long id);  // Restore post from bin
    void clearAllBinPosts();  // Permanently delete all posts in the bin
    List<Post> getAllBinPosts();  // Get all posts in the bin
}
