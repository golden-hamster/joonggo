package com.capstone.joonggo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    public String author;

    public Long memberId;

    public String content;

    public LocalDateTime createdTime;
}
