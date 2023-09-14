package com.capstone.joonggo.dto;

import com.capstone.joonggo.domain.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MarketDto {

    private String title;

    private Integer price;

    private Long postId;

    private LocalDateTime createdTime;

    public MarketDto(String title, Integer price,Long postId, LocalDateTime createdTime) {
        this.title = title;
        this.price = price;
        this.postId = postId;
        this.createdTime = createdTime;
    }
}
