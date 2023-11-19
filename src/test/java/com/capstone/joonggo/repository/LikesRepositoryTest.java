package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.domain.PostStatus;
import com.capstone.joonggo.service.LikesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class LikesRepositoryTest {

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    LikesService likesService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    void findByMemberIdTest() {
        Member member = Member.createMember("t123@test.com", "123", "nick", "nick", 12421, null, null);
        memberRepository.save(member);
        Post post = Post.createPost(member, "test-title", "content", 150000, PostStatus.SALE, null);
        postRepository.save(post);

        likesService.save(member.getId(), post.getId());


        List<Post> posts = likesService.findByMemberId(member.getId());

        for (Post likesPost : posts) {
            System.out.println("post = " + likesPost);
        }

    }

}