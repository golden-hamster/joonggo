package com.capstone.joonggo.config;

import com.capstone.joonggo.service.CustomOAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2MemberService customOAuth2MemberService;
}
