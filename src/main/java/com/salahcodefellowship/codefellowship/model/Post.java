package com.salahcodefellowship.codefellowship.model;

import com.salahcodefellowship.codefellowship.repository.PostRepository;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String body;
    private String createdAt;
    @ManyToOne
    DBUser dbUser;

    public Post() {
    }

    public Post(String body, String createdAt, DBUser dbUser) {
        this.body = body;
        this.createdAt = createdAt;
        this.dbUser = dbUser;
    }

    public Post(String body, String createdAt) {
        this.body = body;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
