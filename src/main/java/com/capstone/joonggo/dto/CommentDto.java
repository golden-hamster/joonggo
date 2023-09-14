package com.capstone.joonggo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    public String author;

    public String loginId;

    public String content;

    public LocalDateTime createdTime;

    public CommentDto(String author, String loginId, String content, LocalDateTime createdTime) {
        this.author = author;
        this.loginId = loginId;
        this.content = content;
        this.createdTime = createdTime;
    }
}
