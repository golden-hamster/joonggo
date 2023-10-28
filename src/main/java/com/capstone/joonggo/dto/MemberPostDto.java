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

}
