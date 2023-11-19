package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Likes {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

    private Long postId;

    public static Likes createLikes(Long memberId, Long postId) {
        Likes likes = new Likes();
        likes.memberId = memberId;
        likes.postId = postId;
        return likes;
    }
}
