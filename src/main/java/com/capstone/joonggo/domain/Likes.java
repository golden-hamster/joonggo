package com.capstone.joonggo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Likes {

    @Id
    private Long memberId;

    @OneToMany()
    private List<Post> post;
}
