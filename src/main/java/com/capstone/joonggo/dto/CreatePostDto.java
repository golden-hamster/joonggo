package com.capstone.joonggo.dto;

import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class CreatePostDto {

    @NotBlank
    @Size(max = 20, message = "제목은 최대 20자까지 입력 가능합니다")
    private String title;

    @Positive
    private Integer price;

    @NotBlank
    private String content;

    private Long memberId;

    private PostStatus postStatus;

    private List<MultipartFile> imageFiles;

    public CreatePostDto(Post post) {
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.content = post.getContent();
        this.memberId = post.getMember().getId();
        this.postStatus = post.getStatus();
        this.imageFiles = null;
    }
}
