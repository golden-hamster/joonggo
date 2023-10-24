package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.LoginType;
import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Role;
import com.capstone.joonggo.dto.MemberJoinDto;
import com.capstone.joonggo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
//    @Transactional
//    public Long join(Member member) {
//        Member savedMember = memberRepository.save(member);
//        return member.getId();
//    }
    @Transactional
    public Long join(MemberJoinDto memberJoinDto) {
        return memberRepository.save(Member.createMember(memberJoinDto.getEmail(),
                passwordEncoder.encode(memberJoinDto.getPassword()),
                memberJoinDto.getNickName(), memberJoinDto.getName(), memberJoinDto.getStudentId(),
                Role.ROLE_USER, LoginType.NORMAL)).getId();
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findAllMember() {
        List<Member> members = memberRepository.findAll();
        return members;
    }

    /**
     * 회원 조회
     */
    public Member findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다"));
        memberRepository.findById(id).orElse(null);
        return member;
    }

    public Member findByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다"));
        return member;
    }


}
