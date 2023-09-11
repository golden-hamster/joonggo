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
    private Long id;

    private String loginId;

    private String password;

    private String nickName;

    private Long studentId;

    private String name;

    private LocalDateTime createdTime;

    public static Member createMember(String loginId, String password,String nickName, String name,
                                      Long studentId) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.name = name;
        member.nickName = nickName;
        member.studentId = studentId;
        member.createdTime = LocalDateTime.now();
        return member;
    }
}
