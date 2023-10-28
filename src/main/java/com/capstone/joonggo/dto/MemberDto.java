package com.capstone.joonggo.dto;

import com.capstone.joonggo.domain.Member;
import lombok.Data;

@Data
public class MemberDto {

    private String nickName;

    public MemberDto(Member member) {
        nickName = member.getNickName();
    }

    public MemberDto(String nickName) {
        this.nickName = nickName;
    }
}
