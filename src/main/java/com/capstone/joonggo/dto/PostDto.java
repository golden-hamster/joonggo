package com.capstone.joonggo.dto;

import com.capstone.joonggo.domain.UploadFile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {

    public String title;

    public String nickName;

    public Integer price;

    public String content;

    public List<UploadFile> imageFiles;

    public LocalDateTime createdTime;

    public PostDto(String title, String nickName, Integer price, String content, List<UploadFile> imageFiles, LocalDateTime createdTime) {
        this.title = title;
        this.nickName = nickName;
        this.price = price;
        this.content = content;
        this.imageFiles = imageFiles;
        this.createdTime = createdTime;
    }
}
