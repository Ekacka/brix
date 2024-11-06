package service;

import model.Post;
import java.util.List;

public class ArchivePostServiceDecorator extends PostServiceDecorator {
    public ArchivePostServiceDecorator(PostService wrappedService) {
        super(wrappedService);
    }

    @Override
    public void deletePost(Long id) {
        System.out.println("Archiving post instead of deleting.");
        wrappedService.archivePost(id);
    }

    @Override
    public List<Post> getArchivedPosts() {
        return wrappedService.getArchivedPosts();
    }
}
