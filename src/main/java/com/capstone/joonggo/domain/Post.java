package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    private Category category;

    private String title;

    private String content;

    private Integer price;

    private LocalDateTime createdDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFile> uploadFiles;

    public static Post createPost(Member member, String title, String content, Integer price, List<UploadFile> uploadFiles) {
        Post post = new Post();
        post.member = member;
        post.title = title;
        post.content = content;
        post.price = price;
        post.uploadFiles = uploadFiles;
        post.createdDate = LocalDateTime.now();
        return  post;
    }

    public void update(String title, String content, Integer price, List<UploadFile> uploadFiles) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.uploadFiles = uploadFiles;
    }
}
