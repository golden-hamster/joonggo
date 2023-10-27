package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findByNickNameTest() {
        Member member = Member.createMember("t123@test.com", "123", "nick", "nick", 12421, null, null);
        memberRepository.save(member);


        Member findMember = memberRepository.findByNickName("nick").get();

        Assertions.assertThat(findMember).isEqualTo(member);

    }
}
