import database.PostRepository;
import database.RuntimePostRepository;
import model.Post;
import model.builder.PostBuilder;
import service.ArchiveManager;
import service.PostService;
import service.RuntimePostService;
import service.ServiceManager;
import service.SimpleArchiveManager;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    static PostService postService;
    static ArchiveManager archiveManager;
    static ServiceManager serviceManager;

    public static void main(String[] args) {

        PostRepository postRepo = new RuntimePostRepository();
        postService = new RuntimePostService(postRepo);
        archiveManager = new SimpleArchiveManager();
        serviceManager = new ServiceManager();

        serviceManager.registerService(postService);

        Scanner sc = new Scanner(System.in);
        mainMenu();
        while (sc.hasNextInt()) {
            int pressedKey = sc.nextInt();
            switch (pressedKey) {
                case 1 -> createPostMenu();
                case 2 -> displayAllPosts();
                case 3 -> deletePostMenu();
                case 4 -> editPostMenu();
                case 5 -> undoLastAction();
                case 6 -> archivePostMenu();
                case 7 -> displayArchivedPosts();
                case 8 -> restorePostMenu(); // New option to restore
                case 9 -> clearAllArchivedPosts(); // New option to clear archive
            }
        }
        sc.close();
    }

    private static void undoLastAction() {
        serviceManager.undoLastCommand();
        System.out.println("Last action undone.");
        mainMenu();
    }

    private static void createPostMenu() {
        PostBuilder builder = new PostBuilder();
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the title.");
        String title = sc.nextLine();
        System.out.println("Input the content.");
        String content = sc.nextLine();
        System.out.println("Input the name of poster.");
        String postedBy = sc.nextLine();

        builder.setCreatedAt(new Date());
        Post post = builder.setTitle(title).setContent(content).setPostedBy(postedBy).build();
        postService.createPost(post);

        System.out.println("Post created successfully.");
        mainMenu();
    }

    private static void displayAllPosts() {
        for (Post post : postService.getAllPosts()) {
            System.out.println("---------");
            System.out.printf("Title: %s Id: %d%n", post.getTitle(), post.getId());
            System.out.printf("Content: %s%n", post.getContent());
            System.out.printf("Posted by: %s%n", post.getPostedBy());
            System.out.printf("Created at: %s%n", post.getCreatedAt().toString());
            System.out.println("---------");
        }
        if (postService.getAllPosts().isEmpty()) {
            System.out.println("No posts yet.");
        }
        mainMenu();
    }

    private static void deletePostMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input id of post that you want to delete.");
        long id = sc.nextLong();

        Post post = postService.getPostById(id);
        if (post != null) {
            postService.deletePost(id);
            System.out.println("Post deleted successfully.");
        } else {
            System.out.println("Post with id " + id + " does not exist.");
        }
        mainMenu();
    }

    private static void editPostMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the ID of the post you want to edit:");
        long id = sc.nextLong();
        sc.nextLine();
        System.out.println("Enter the new content:");
        String newContent = sc.nextLine();

        Post post = postService.getPostById(id);
        if (post != null) {
            post.setContent(newContent);
            postService.updatePost(id, post);
            System.out.println("Post edited successfully.");
        } else {
            System.out.println("Post with ID " + id + " not found.");
        }
        mainMenu();
    }

    private static void archivePostMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the ID of the post you want to move to bin:");
        long id = sc.nextLong();

        Post post = postService.getPostById(id);
        if (post != null) {
            archiveManager.archivePost(post);
            postService.deletePost(id);
            System.out.println("Post moved to bin successfully.");
        } else {
            System.out.println("Post not found.");
        }
        mainMenu();
    }

    private static void displayArchivedPosts() {
        List<Post> archivedPosts = archiveManager.getAllArchivedPosts();
        for (Post post : archivedPosts) {
            System.out.println("---------");
            System.out.printf("Title: %s Id: %d%n", post.getTitle(), post.getId());
            System.out.printf("Content: %s%n", post.getContent());
            System.out.printf("Posted by: %s%n", post.getPostedBy());
            System.out.printf("Created at: %s%n", post.getCreatedAt().toString());
            System.out.println("---------");
        }
        if (archivedPosts.isEmpty()) {
            System.out.println("No posts in bin.");
        }
        mainMenu();
    }

    private static void restorePostMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the ID of the post you want to restore:");
        long id = sc.nextLong();

        Post restoredPost = archiveManager.restorePostById(id);
        if (restoredPost != null) {
            postService.createPost(restoredPost);
            System.out.println("Post restored successfully.");
        } else {
            System.out.println("No deleted post found with ID: " + id);
        }
        mainMenu();
    }

    private static void clearAllArchivedPosts() {
        archiveManager.clearAllArchives();
        System.out.println("Posts deleted permanently");
        mainMenu();
    }

    private static void mainMenu() {
        System.out.println("Press 1 to create post.");
        System.out.println("Press 2 to see all posts.");
        System.out.println("Press 3 to delete post permanently.");
        System.out.println("Press 4 to edit post.");
        System.out.println("Press 5 to undo last action.");
        System.out.println("Press 6 to move post in bin.");
        System.out.println("Press 7 to see bin.");
        System.out.println("Press 8 to restore post from bin.");
        System.out.println("Press 9 to clear bin.");
    }
}
