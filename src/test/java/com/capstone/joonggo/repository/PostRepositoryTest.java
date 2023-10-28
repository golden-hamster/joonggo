package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.*;
import com.capstone.joonggo.dto.CreatePostDto;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void findByMemberNickNameTest() {
        Member member = Member.createMember("t123@test.com", "123", "nick", "nick", 12421, null, null);
        memberRepository.save(member);

        Post post = Post.createPost(member, "test-title", "content", 150000, PostStatus.SALE, null);
        postRepository.save(post);

        List<Post> byMemberNickName = postRepository.findByMemberNickName(member.getNickName());
        Assertions.assertThat(byMemberNickName.get(0)).isEqualTo(post);
    }
}
