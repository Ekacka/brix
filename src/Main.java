import database.PostRepository;
import database.RuntimePostRepository;
import model.Post;
import model.builder.PostBuilder;
import service.BinManager;
import service.PostService;
import service.RuntimePostService;
import service.ServiceManager;
import service.SimpleBinManager;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    static PostService postService;
    static BinManager binManager;
    static ServiceManager serviceManager;

    public static void main(String[] args) {

        PostRepository postRepo = new RuntimePostRepository();
        postService = new RuntimePostService(postRepo);
        binManager = new SimpleBinManager();
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
                case 6 -> binManagementMenu(); // Enter Bin Management Menu
                default -> System.out.println("Invalid option. Please try again.");
            }
            mainMenu();
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
    }

    // New Bin Management Menu
    private static void binManagementMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Bin Management ===");
        System.out.println("Press 1 to move post to bin.");
        System.out.println("Press 2 to view all bin posts.");
        System.out.println("Press 3 to restore post from bin.");
        System.out.println("Press 4 to permanently delete all bin posts.");
        System.out.println("Press 0 to return to main menu.");

        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> binPostMenu();
            case 2 -> displayBinPosts();
            case 3 -> restorePostMenu();
            case 4 -> clearAllBinPosts();
            case 0 -> mainMenu();
            default -> System.out.println("Invalid option. Returning to main menu.");
        }
    }

    private static void binPostMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the ID of the post you want to move to the bin:");
        long id = sc.nextLong();

        Post post = postService.getPostById(id);
        if (post != null) {
            binManager.binPost(post);
            postService.deletePost(id);
            System.out.println("Post moved to bin successfully.");
        } else {
            System.out.println("Post not found.");
        }
    }

    private static void displayBinPosts() {
        List<Post> binPosts = binManager.getAllBinPosts();
        for (Post post : binPosts) {
            System.out.println("---------");
            System.out.printf("Title: %s Id: %d%n", post.getTitle(), post.getId());
            System.out.printf("Content: %s%n", post.getContent());
            System.out.printf("Posted by: %s%n", post.getPostedBy());
            System.out.printf("Created at: %s%n", post.getCreatedAt().toString());
            System.out.println("---------");
        }
        if (binPosts.isEmpty()) {
            System.out.println("No posts in the bin.");
        }
    }

    private static void restorePostMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the ID of the post you want to restore from the bin:");
        long id = sc.nextLong();

        Post restoredPost = binManager.restorePostById(id);
        if (restoredPost != null) {
            postService.createPost(restoredPost);
            System.out.println("Post restored successfully.");
        } else {
            System.out.println("No post found in the bin with ID: " + id);
        }
    }

    private static void clearAllBinPosts() {
        binManager.clearAllBinPosts();
        System.out.println("All posts in the bin have been permanently deleted.");
    }

    private static void mainMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("Press 1 to create post.");
        System.out.println("Press 2 to see all posts.");
        System.out.println("Press 3 to delete post.");
        System.out.println("Press 4 to edit post.");
        System.out.println("Press 5 to undo last action.");
        System.out.println("Press 6 to manage bin.");
        System.out.println("Press 0 to exit.");
    }
}
