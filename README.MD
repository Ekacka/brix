# Blogging Platform

SE-2329 <br>
Members: Arsen Tinalin, Tamerlan Kairbek, Bekzhan Kuanysh, Sanzhar Yesbossynov <br> <br>

This project is a simple blogging platform built as a console application in Java. It allows users to create, view, and
manage blog posts. This `README` provides a comprehensive overview of the platform, including system architecture,
design patterns, UML diagrams, and usage instructions.

## Usage Instructions

1. **Clone the Repository**

   git clone https://github.com/arxshi/brix
   cd blogging-platform-console

## Features

- **Create Post**: Users can create new posts with title, content, and the author's name.
- **View All Posts**: Display all posts stored in the system.
- **Delete Post**: Users can delete posts from the system.
- **Edit Post**: Modify an existing post's content.
- **Undo Last Action**: Rollback the most recent change (create, delete, edit).
- **Bin Management**: Manage deleted posts (move to bin, restore, or permanently delete them).

## System Architecture

**MVC (Model-View-Controller)**

**Where**: RuntimePostRepository (Model), Console (View), and Service (Controller).

- **Purpose**: Separates concerns by keeping the data model, the user interface, and the business logic distinct.
- **Role**: The MVC pattern enhances modularity by allowing the user interface to be easily separated or modified
  without affecting the underlying data handling logic.

# UML Diagram

This diagram represents the classes and their relationships in the project.

## Class Structure

- **Main**
    - → Depends on ServiceManager to start the application

- **ServiceManager**
    - → Manages instances of PostService

- **PostService** (interface)
    - Methods: createPost(), editPost(), deletePost()
    - → Uses PostRepository to manage posts
    - → May interact with BinManager to handle deleted posts

- **PostRepository** (interface)
    - Methods: savePost(), findPostById(), deletePost()

    - Implementations:
        - **RuntimePostRepository**
            - ↳ Implements PostRepository for in-memory storage

- **PostServiceDecorator** (abstract class)
    - ↳ Extends PostService, allowing for service extension via decorators
    - ↳ **ArchivePostServiceDecorator**
        - Adds functionality to archive posts

- **BinManager** (interface)
    - Implementation:
        - **SimpleBinManager**
            - Manages deleted posts (e.g., a trash bin)

- **Command** (interface)
    - Implementation:
        - **EditPostCommand**
            - Command for editing a post

- **PostNotFoundException** (exception)
    - Thrown if a post is not found

## Dependencies and Inheritance

```
Main --> ServiceManager --> PostService --> PostRepository
PostRepository <|-- RuntimePostRepository

PostService <|.. PostServiceDecorator
PostServiceDecorator <|-- ArchivePostServiceDecorator

PostService --> BinManager
BinManager <|-- SimpleBinManager

Command <|.. EditPostCommand
```

# Design Patterns

### 1. **Singleton**

The **Singleton** pattern ensures that only one instance of the ServiceManager
class is created throughout the application.
This instance manages services such as
PostService and BinManager, preventing the creation of multiple
instances of these services.

### 2. **Builder**

The **Builder** pattern is used to construct a Post object
step by step. It allows you to set properties such as
title, content, and postedBy, ensuring that the
post is correctly built before it is used.

### 3. **Strategy**

The **Strategy** pattern is used to define a family
of algorithms for managing posts and bin operations.
It allows switching between different behaviors
(e.g., in-memory storage vs database storage)
at runtime without altering the code logic.

### 4. **Command**

The Command pattern encapsulates requests as objects, allowing users to execute actions like editing posts. This
decouples the request sender (e.g., user input) from the request handler (e.g., PostService).

### 5. **Decorator**

The **Decorator** pattern is used to add additional responsibilities to an object dynamically. In this project, the
Decorator pattern is applied to extend the functionality of the PostService without modifying the original PostService
implementation.

### 6. **Bridge**

The Bridge pattern separates abstraction (e.g., PostService) from implementation (e.g., PostRepository). This allows for
changes in storage or retrieval methods without affecting the core logic of post management.

# Assumptions and Limitations of the Implemented System

---

## Assumptions

1. **Single Instance of Services:**
    - The system assumes that only one instance of `PostService` and `BinManager` is used throughout the application, as
      indicated by the singleton-like behavior of `ServiceManager` registering the services only once.

2. **In-Memory Storage for Posts:**
    - The posts are managed in-memory (using `RuntimePostRepository`), which assumes the system does not need persistent
      storage for posts. Once the application is closed, all data is lost unless the implementation is extended to
      support persistent storage (e.g., a database).

3. **Command Pattern for Post Editing:**
    - The **Command Pattern** is used to handle undo functionality (`undoLastAction`). This assumes that every action
      can be undone by simply executing the reverse command, which might not always be possible in complex systems where
      the state changes unpredictably.

4. **Simple Bin Management:**
    - The bin management assumes a very basic "move-to-bin" approach, where posts are moved to an in-memory list and can
      be restored or permanently deleted. It does not consider data persistence, and once the program is terminated, the
      bin is emptied.

5. **Assumption of Valid Post Data:**
    - The system does not handle invalid input gracefully when creating or editing posts. For example, it does not check
      for empty or malformed data (such as an empty title or content). It assumes the user always provides valid inputs.

6. **No Authentication or Authorization:**
    - The system does not implement any user authentication or authorization mechanisms, meaning anyone can create,
      edit, or delete posts. This is fine for a small-scale prototype but would be a major limitation for a real-world
      application where user roles and permissions are critical.

7. **Assumption of Sequential User Input:**
    - The system expects the user to provide input in a specific sequence (e.g., title, content, postedBy). It assumes
      that users will follow the correct input format, with no flexibility for error handling or user guidance.

8. **Error Handling:**
    - Error handling is quite minimal, especially for actions like post deletion or editing. If a post ID is invalid,
      the system simply outputs a message but does not provide detailed feedback or options for recovery.

9. **System Focus:**
    - The system is designed primarily for a **single user** or admin, who can interact with all features. There's no
      multi-user support, meaning one user operates the system at a time.

---

## Limitations

1. **No Persistence Across Sessions:**
    - The current implementation does not support persistent storage, meaning all posts and their states (including bin
      management) are lost when the application is closed. In a real-world application, data should be stored in a
      database to ensure persistence.

2. **Limited Undo Functionality:**
    - The system uses a basic **undo** functionality implemented through the command pattern. However, only the last
      action can be undone, and there is no concept of managing multiple levels of undo/redo for more complex
      operations. This might limit its usefulness for complex workflows.

3. **Basic User Interface:**
    - The interface is command-line-based with minimal user interaction. It doesn't provide any validation feedback or
      user-friendly prompts. For example, there is no feedback when a user inputs an invalid ID for post operations, and
      navigation options are limited.

4. **Potential Data Loss in the Bin:**
    - Since the system uses in-memory data structures, once the program is closed, all posts in the bin are lost.
      There's no safeguard against accidental data loss, and no way to export or back up the posts in the bin before
      deletion.

5. **Limited Post Editing:**
    - The system only allows basic editing of post content (changing the text) but doesn't support editing other post
      properties, such as the title or postedBy. A more flexible editing system might be required.

6. **No Advanced Search Capabilities:**
    - The system does not support searching for posts by title, content, date, or other attributes. This could become a
      major limitation as the number of posts grows.

7. **No Support for Image or File Attachments:**
    - The current implementation focuses only on the text content of posts, with no support for adding images, files, or
      rich media. This could be a significant limitation for modern blogging or content management systems.

8. **No Multi-threading or Concurrency Handling:**
    - The current design assumes a single-threaded environment. There is no handling for concurrency or multi-threaded
      operations, meaning multiple users or operations might conflict if the system is extended to support multi-user
      access or background tasks.

9. **Hardcoded Menu Options:**
    - The system's menu options and flow are hardcoded in the Main class. As the number of features grows, maintaining
      and scaling the system could become difficult without refactoring the menu system into more modular components.

10. **No Unit Testing:**
    - There is no indication that unit tests are used to verify the functionality of the system. In real-world
      applications, testing is crucial, especially when changes are made to core business logic like post creation,
      deletion, and bin management.
