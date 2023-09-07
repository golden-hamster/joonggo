package com.capstone.joonggo.dto;

import lombok.Data;

@Data
public class LoginDto {

private String loginId;

private String password;

    public LoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
