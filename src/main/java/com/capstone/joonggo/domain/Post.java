package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    private String title;

    private String content;

    private Integer price;

    private String createdDate;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFile> uploadFiles;

    private Integer likesCount;

    public static Post createPost(Member member, String title, String content, Integer price, PostStatus status, List<UploadFile> uploadFiles) {
        Post post = new Post();
        post.member = member;
        post.title = title;
        post.content = content;
        post.price = price;
        post.uploadFiles = uploadFiles;
        post.status = status;
        post.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        post.likesCount = 0;
        return post;
    }

    public void update(String title, String content, Integer price, List<UploadFile> uploadFiles) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.uploadFiles.clear();
        for (UploadFile uploadFile : uploadFiles) {
            this.uploadFiles.add(uploadFile);
        }
    }

    public void updateStatus(PostStatus status) {
        this.status = status;
    }

    public void addLikesCount() {
        this.likesCount++;
    }

    public void subLikesCount() {
        this.likesCount--;
    }
}
