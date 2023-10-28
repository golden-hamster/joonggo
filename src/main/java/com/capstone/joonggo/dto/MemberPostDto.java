package com.capstone.joonggo.dto;

import com.capstone.joonggo.domain.PostStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberPostDto {

    private String title;

    private Integer price;

    private Long postId;

    private String thumbnailName;

    private PostStatus status;

    private LocalDateTime createdDate;

    public MemberPostDto(String title, Integer price, Long postId, String thumbnailName, PostStatus status, LocalDateTime createdDate) {
        this.title = title;
        this.price = price;
        this.postId = postId;
        this.thumbnailName = thumbnailName;
        this.status = status;
        this.createdDate = createdDate;
    }
}
