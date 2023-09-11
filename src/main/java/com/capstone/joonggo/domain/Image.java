package com.capstone.joonggo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

//@Entity
//@Getter
public class Image {

//    @GeneratedValue
//    @Id
//    @Column(name = "image_id")
    private Long id;

    private String uploadImageName;

    private String storedImageName;

    private LocalDateTime createdDate;

}
