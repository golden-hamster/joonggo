package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        Member savedMember = memberRepository.save(member);
        return member.getId();
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
        Member member = memberRepository.findById(id).get();
        return member;
    }

    public Member findByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).get();
        return member;
    }


}
