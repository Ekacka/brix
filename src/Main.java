import database.PostRepository;
import database.RuntimePostRepository;
import model.Post;
import model.builder.PostBuilder;
import service.PostService;
import service.RuntimePostService;
import service.ServiceManager;

import java.util.Date;
import java.util.Scanner;

public class Main {

    static PostRepository postRepo;
    static PostService postService;
    static ServiceManager serviceManager;

    public static void main(String[] args) {

        postRepo = new RuntimePostRepository();
        postService = new RuntimePostService(postRepo);
        serviceManager = new ServiceManager();

        serviceManager.registerService(postService);

        Scanner sc = new Scanner(System.in);
        mainMenu();
        while (sc.hasNextInt()) {
            int pressedKey = sc.nextInt();
            switch (pressedKey) {
                case 1 -> createPostMenu();
                case 2 -> {
                    PostService service = serviceManager.getService(PostService.class);
                    for (Post post : service.getAllPosts()) {
                        System.out.println("---------");
                        System.out.printf("Title: %s Id: %d%n", post.getTitle(), post.getId());
                        System.out.printf("Content: %s%n", post.getContent());
                        System.out.printf("Posted by: %s%n", post.getPostedBy());
                        System.out.printf("%nCreated at: %s%n", post.getCreatedAt().toString());
                        System.out.println("---------");
                    }
                    if(service.getAllPosts().isEmpty()) {
                        System.out.println("No posts yet.");
                    }
                    mainMenu();
                }
                case 3 -> deletePostMenu();
            }
        }
        sc.close();
    }

    private static void createPostMenu() {
        PostBuilder builder = new PostBuilder();

        String title;
        String content;
        String postedBy;

        Scanner sc = new Scanner(System.in);
        System.out.println("Input the title.");
        title = sc.nextLine();
        System.out.println("Input the content.");
        content = sc.nextLine();
        System.out.println("Input the name of poster.");
        postedBy = sc.nextLine();

        builder.setCreatedAt(new Date());

        mainMenu();

        PostService service = serviceManager.getService(PostService.class);
        service.createPost(builder.setTitle(title).setContent(content).setPostedBy(postedBy).build());
    }

    private static void deletePostMenu() {
        Scanner sc = new Scanner(System.in);
        long id;
        System.out.println("Input id of post that you want to delete.");
        id = sc.nextLong();

        PostService service = serviceManager.getService(PostService.class);

        mainMenu();

        service.deletePost(id);
    }

    private static void mainMenu() {
        System.out.println("Press 1 to create post.");
        System.out.println("Press 2 to see all posts.");
        System.out.println("Press 3 to delete post.");
    }
}