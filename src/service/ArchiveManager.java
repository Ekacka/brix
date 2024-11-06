package service;

import model.Post;
import java.util.List;

public interface ArchiveManager {
    void archivePost(Post post);
    Post restorePostById(Long id);
    void clearAllArchives();
    List<Post> getAllArchivedPosts();
}
