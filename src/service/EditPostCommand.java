package service;

import model.Post;
import service.PostService;

public class EditPostCommand implements Command {
    private final PostService postService;
    private final long postId;
    private final String newContent;
    private String previousContent;

    public EditPostCommand(PostService postService, long postId, String newContent) {
        this.postService = postService;
        this.postId = postId;
        this.newContent = newContent;
    }

    @Override
    public void execute() {
        Post post = postService.getPostById(postId);
        if (post != null) {
            previousContent = post.getContent();
            post.setContent(newContent);
            postService.updatePost(postId, post);
        } else {
            System.out.println("Post not found.");
        }
    }

    @Override
    public void undo() {
        Post post = postService.getPostById(postId);
        if (post != null && previousContent != null) {
            post.setContent(previousContent);
            postService.updatePost(postId, post);
        } else {
            System.out.println("Unable to undo edit.");
        }
    }
}
