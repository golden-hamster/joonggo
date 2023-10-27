package com.capstone.joonggo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private String nickName;

    private String title;

    private Integer price;

    private Long postId;

    private String thumbnailName;

    private LocalDateTime createdDate;

}
