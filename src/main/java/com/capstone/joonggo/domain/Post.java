package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;

    private String content;

    private Long price;

    private LocalDateTime createdDate;

//    private List<Image> images;

    public static Post createPost(Member member, String title, String content, Long price) {
        Post post = new Post();
        post.member = member;
        post.title = title;
        post.content = content;
        post.price = price;
        post.createdDate = LocalDateTime.now();
        return  post;
    }
}
