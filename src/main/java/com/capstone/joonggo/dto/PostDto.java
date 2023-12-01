package com.capstone.joonggo.dto;

import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.domain.PostStatus;
import com.capstone.joonggo.domain.UploadFile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostDto {

    public Long postId;

    public String email;

    public String title;

    public String nickName;

    public Integer price;

    public String content;

    public List<String> storeFileNames;

    public String createdTime;

    public PostStatus status;

    public PostDto(String title, String nickName, Integer price, String content, List<String> storeNames, String createdTime) {
        this.title = title;
        this.nickName = nickName;
        this.price = price;
        this.content = content;
        this.storeFileNames = storeNames;
        this.createdTime = createdTime;
    }

    public PostDto(Post post) {
        postId = post.getId();
        email = post.getMember().getEmail();
        title = post.getTitle();
        nickName = post.getMember().getNickName();
        price = post.getPrice();
        content = post.getContent();
        storeFileNames = post.getUploadFiles().stream()
                .map(UploadFile::getStoreName)
                .collect(Collectors.toList());
        status = post.getStatus();
    }
}
