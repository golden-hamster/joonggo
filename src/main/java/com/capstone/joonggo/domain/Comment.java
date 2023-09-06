package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    private LocalDateTime createdTime;

    public static Comment createComment(Post post, Member member, String content) {
        Comment cmt = new Comment();
        cmt.post = post;
        cmt.member = member;
        cmt.content = content;
        cmt.createdTime = LocalDateTime.now();
        return cmt;
    }
}
