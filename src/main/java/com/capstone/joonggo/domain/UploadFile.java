package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "upload_file")
public class UploadFile {

    @GeneratedValue
    @Id
    @Column(name = "file_id")
    private Long id;

    private String originalName;

    private String storeName;

    private LocalDateTime createdDate;

    public static UploadFile createUploadFile(String originalName, String storeName) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.originalName = originalName;
        uploadFile.storeName = storeName;
        uploadFile.createdDate = LocalDateTime.now();
        return uploadFile;
    }

}
