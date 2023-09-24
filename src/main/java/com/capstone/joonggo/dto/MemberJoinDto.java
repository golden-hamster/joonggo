package com.capstone.joonggo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberJoinDto {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;

    @NotBlank
    private String name;

    private Integer studentId;


}
