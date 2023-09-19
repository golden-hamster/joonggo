package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostService postService;
    @Autowired
    MemberService memberService;



    @Test
    void update() {
        Member member = Member.createMember("123", "123", "123", "123", 123);

        Post post = Post.createPost(member, "testTitle", "test", 10000, null);

        Long postId = postService.save(post);

        Assertions.assertThat(post.getPrice()).isEqualTo(10000);

        Post findPost = postService.findById(postId);



    }
}
