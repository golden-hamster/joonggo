package com.capstone.joonggo.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "012345678901234567890123456789012";
}
