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

    private int studentId;

    private String name;

    private LocalDateTime createdDate;

    public static Member createMember(String loginId, String password,String nickName, String name,
                                      int studentId) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.name = name;
        member.nickName = nickName;
        member.studentId = studentId;
        member.createdDate = LocalDateTime.now();
        return member;
    }
}
