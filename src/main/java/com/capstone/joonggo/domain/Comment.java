package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
@Getter
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    private String createdDate;

    public void setContent(String content) {
        this.content = content;
    }

    public static Comment createComment(Post post, Member member, String content) {
        Comment cmt = new Comment();
        cmt.post = post;
        cmt.member = member;
        cmt.content = content;
        cmt.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        return cmt;
    }
}
