package com.capstone.joonggo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    public Long commentId;

    public String author;

    public String email;

    @NotBlank
    public String content;

    public LocalDateTime createdDate;

    public CommentDto(Long commentId, String author, String email, String content, LocalDateTime createdTime) {
        this.commentId = commentId;
        this.author = author;
        this.email = email;
        this.content = content;
        this.createdDate = createdTime;
    }
}
