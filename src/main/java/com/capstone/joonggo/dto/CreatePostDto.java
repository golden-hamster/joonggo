package com.capstone.joonggo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreatePostDto {

    private String title;

    private int price;

    private String content;

    private List<MultipartFile> imageFiles;
}
