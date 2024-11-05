package model;

import java.util.Date;

public class Post {

    private Long id;
    private String title;
    private String content;
    private String postedBy;
    private Date createdAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
