package com.capstone.joonggo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    public Long commentId;

    public String author;

    public String loginId;

    @NotBlank
    public String content;

    public LocalDateTime createdDate;

    public CommentDto(Long commentId, String author, String loginId, String content, LocalDateTime createdTime) {
        this.commentId = commentId;
        this.author = author;
        this.loginId = loginId;
        this.content = content;
        this.createdDate = createdTime;
    }
}
