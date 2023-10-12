package com.capstone.joonggo.security;

import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class JwtTokenizerTest {

    @Value("${jwt.secretKey}")
    String accessSecret;

    public final Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; // 30분

    @Test
    public void createToken() throws Exception {
        String email = "testEmail@test.com";
        List<String> roles = List.of("ROLE_USER"); // [ "ROLE_USER" ]
        Long id = 1L;
        ClaimsBuilder claims = Jwts.claims().subject(email);// JWT 토큰의 payload 에 들어갈 내용(claims)을 설정
        claims.add("roles", roles);
        claims.add("memberId", id);

        //secretKey 값을 byte 배열로 바꿔줌
        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);

        String jwtToken = Jwts.builder() // builder 는 JwtBuilder 를 반환. Builder 패턴
                .claims((Map<String, ?>) claims) // claims 가 추가된 JwtBuilder 를 반환
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + this.ACCESS_TOKEN_EXPIRE_COUNT)) // 현재 시간으로부터 30분뒤에 만료
                .signWith(Keys.hmacShaKeyFor(accessSecret)) // 결과에 서명까지 포함시킨 JwtBuilder 반환
                .compact();

        System.out.println(jwtToken);

    }

    @Test
    public void parseToken() throws Exception {
        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0RW1haWxAdGVzdC5jb20iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwibWVtYmVySWQiOjEsImlhdCI6MTY5NzA5ODA3NywiZXhwIjoxNjk3MDk5ODc3fQ.6N5zcM_dRv2sLceX2IZ58m9lMOagHM2NiPToVxl522M";
//        byte[] payload = Jwts.parser()
//                .decryptWith(Keys.hmacShaKeyFor(accessSecret))
//                .build()
//                .parseSignedContent(jwtToken)
//                .getPayload();
    }
}
