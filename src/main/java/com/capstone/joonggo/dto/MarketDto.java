package com.capstone.joonggo.dto;

import com.capstone.joonggo.domain.PostStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MarketDto {

    private String title;

    private Integer price;

    private Long postId;

    private String thumbnailName;

    private LocalDateTime createdDate;

    private PostStatus status;

    public MarketDto(String title, Integer price,Long postId, String thumbnailName,LocalDateTime createdDate, PostStatus status) {
        this.title = title;
        this.price = price;
        this.postId = postId;
        this.thumbnailName = thumbnailName;
        this.createdDate = createdDate;
        this.status = status;
    }
}
