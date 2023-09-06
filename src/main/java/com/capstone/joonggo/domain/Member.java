package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    @GeneratedValue
    @Id
    @Column(name = "member_id")
    private Long memberId;

    private String loginId;

    private String password;

    private String name;

    private LocalDateTime createdTime;

    public static Member createMember(String loginId, String password, String name) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.name = name;
        member.createdTime = LocalDateTime.now();
        return member;
    }
}
