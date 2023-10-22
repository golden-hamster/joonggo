package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.LoginType;
import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.domain.Role;
import com.capstone.joonggo.dto.CreatePostDto;
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

/**

    @Test
    void update() {
        Member member = Member.createMember("123", "123", "123", "123", 123, Role.ROLE_USER, LoginType.NORMAL);

        Post post = Post.createPost(member, "testTitle", "test", 10000, null, null);

        Long postId = postService.save(post, null);

        Assertions.assertThat(post.getPrice()).isEqualTo(10000);

        postService.update(postId, "updateTitle", "updateContent", 15000, null);

        Assertions.assertThat(post.getPrice()).isEqualTo(15000);

    }

 */
}
