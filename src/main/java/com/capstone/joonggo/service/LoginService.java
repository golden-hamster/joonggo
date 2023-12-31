package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String email, String password) {

        Optional<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            // 조회 결과가 있을 시(해당 아이디를 가진 회원 정보가 있다)
            Member member = byEmail.get();
            if (member.getPassword().equals(password)) {
                // 비밀번호 일치
                return member;
            } else {
                // 비밀번호 불일치
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 아이디를 가진 회원이 없다)
            return null;
        }

    }
}
