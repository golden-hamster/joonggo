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
    private Long fileId;

    private String uploadName;

    private String storeName;

    private LocalDateTime createdDate;

    public static UploadFile createUploadFile(String uploadName, String storeName) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.uploadName = uploadName;
        uploadFile.storeName = storeName;
        uploadFile.createdDate = LocalDateTime.now();
        return uploadFile;
    }

}
