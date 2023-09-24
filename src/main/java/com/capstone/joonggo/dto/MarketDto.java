package com.capstone.joonggo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MarketDto {

    private String title;

    private Integer price;

    private Long postId;

    private String thumbnailName;

    private LocalDateTime createdDate;

    public MarketDto(String title, Integer price,Long postId, String thumbnailName,LocalDateTime createdDate) {
        this.title = title;
        this.price = price;
        this.postId = postId;
        this.thumbnailName = thumbnailName;
        this.createdDate = createdDate;
    }
}
