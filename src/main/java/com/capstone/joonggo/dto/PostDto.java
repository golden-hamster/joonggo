package com.capstone.joonggo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    public String title;

    public String nickName;

    public int price;

    public String content;

    public LocalDateTime createdTime;
}
