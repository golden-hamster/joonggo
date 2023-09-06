package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @Column(name = "member_id")
    private Member member;

    @Column(name = "comment_id")
    private Comment comment;

    private String title;

    private String content;

    private LocalDateTime createdTime;

    public static Post createPost(Member member, String title, String content) {
        Post post = new Post();
        post.member = member;
        post.title = title;
        post.content = content;
        post.createdTime = LocalDateTime.now();
        return  post;
    }
}
