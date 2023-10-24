package com.capstone.joonggo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberJoinDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 20)
    private String password;

    @NotBlank
    @Size(max = 12)
    private String nickName;

    @NotBlank
    private String name;

    private Integer studentId;


}
