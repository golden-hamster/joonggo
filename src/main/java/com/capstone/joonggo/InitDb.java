package com.capstone.joonggo;

import com.capstone.joonggo.domain.Comment;
import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.service.CommentService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInit();
        initService.dbInit2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;
        private final CommentService commentService;

        public void dbInit() {
            Member member1 = Member.createMember("test1", "test", "test1", "test", 123);
            Member member2 = Member.createMember("test2", "test", "test2", "test", 124);
            Member member3 = Member.createMember("test3", "test", "test3", "test", 125);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            Post testPost1 = Post.createPost(member1, "Test title1", "content is...", 15000, null);
            Post testPost2 = Post.createPost(member2, "Test title2", "content is...", 25000, null);
            Post testPost3 = Post.createPost(member3, "Test title3", "content is...", 35000, null);

            em.persist(testPost1);
            em.persist(testPost2);
            em.persist(testPost3);

            Comment comment1 = Comment.createComment(testPost1, member1, "Comment is...");
            Comment comment2 = Comment.createComment(testPost1, member2, "Comment is...");
            Comment comment3 = Comment.createComment(testPost1, member3, "Comment is...");
            em.persist(comment1);
            em.persist(comment2);
            em.persist(comment3);
        }

        public void dbInit2() {
            for (int i = 1; i <= 1; i++) {
                // 멤버 생성
                Member member = Member.createMember("test" + i, "test", "test" + i, "test", 120 + i);
                em.persist(member);
                // 글 생성
                for (int j = 1; j <= 1000; j++) {
                    Post post = Post.createPost(member, "Test title" + j, "content is...", 15000 + (j * 1000), null);
                    em.persist(post);

                    // 댓글 생성
                    for (int k = 1; k <= 10; k++) {
                        Comment comment = Comment.createComment(post, member, "Comment is...");
                        em.persist(comment);
                    }
                }
            }
        }
    }
}
