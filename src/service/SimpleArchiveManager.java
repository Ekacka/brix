package service;

import model.Post;
import java.util.ArrayList;
import java.util.List;

public class SimpleArchiveManager implements ArchiveManager {

    private final List<Post> archivedPosts = new ArrayList<>();

    @Override
    public void archivePost(Post post) {
        archivedPosts.add(post);
    }

    @Override
    public Post restorePostById(Long id) {
        for (Post post : archivedPosts) {
            if (post.getId().equals(id)) {
                archivedPosts.remove(post);
                return post;
            }
        }
        System.out.println("No archived post found with ID: " + id);
        return null;
    }

    @Override
    public void clearAllArchives() {
        archivedPosts.clear();
        System.out.println("All archived posts have been cleared.");
    }

    @Override
    public List<Post> getAllArchivedPosts() {
        return new ArrayList<>(archivedPosts);
    }
}
